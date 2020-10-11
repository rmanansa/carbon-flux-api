package com.noodle.pfai.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * The ThirdPartyApiService provides API-to-API integration with web services
 * deployed outside our Noodle Network. This third-party APIs which are usually with our
 * partners provide additional functionalities to our applications required by our common customer.
 */
@Service
public class ThirdPartyApiService {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyApiService.class);
    private static final String PARTNER_API_ERROR = "{\"message\":\"there is an issue with partner api\"}";
    private static final String PFAI_SERVICE_LOG = "{\"pfai-api-service\": \"{}\"}";
    private static final String THIRD_PARTY_API_LOG = "{\"pfai-api-3rd-service\": \"{}\"}";
    private RestTemplate restTemplate;
    private String partnerURL;
    private String apiKey;
    private String excludeList;

    @Autowired
    public ThirdPartyApiService(RestTemplateBuilder restTemplateBuilder,
                                @Value("${partner.api.url}") String partnerURL,
                                @Value("${partner.api.key}") String apiKey,
                                @Value("${partner.api.exclude.list}") String excludeList
                                ) {
        this.restTemplate = restTemplateBuilder.build();
        this.partnerURL = partnerURL;
        this.apiKey = apiKey;
        this.excludeList = excludeList;
    }

    public ResponseEntity<String> getWeather(Double lat, Double lon) {

        ResponseEntity<String> responseEntity;
        StringBuilder urlWithParams = new StringBuilder(partnerURL);
        urlWithParams.append("/onecall?lat={lat}&lon=-{lon}&exclude={exclude}&appid={appid}");
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("lat", lat.toString());
        urlParams.put("lon", lon.toString());
        urlParams.put("exclude", excludeList);
        urlParams.put("appid", apiKey);
        log.info("{\"method\": \"service.getWeather\"}");
        log.info("partnerURL = {}", partnerURL);

        //URL from config = "https://api.openweathermap.org/data/2.5";
        // action = "onecall";
        // lat = 33.441792 //Double
        // lon = 94.837689 / Double
        // exclude  determines which data to take out. get this from the config

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(urlWithParams.toString());
        final String uri = uriBuilder.buildAndExpand(urlParams).toUri().toString();
        log.info("{\"uri\": \"{}\"}", uri);


        try {
            responseEntity = restTemplate.getForEntity(uri, String.class);
            return handleResponse(responseEntity);
        } catch (RestClientException e) {
            log.error(PFAI_SERVICE_LOG, e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(PARTNER_API_ERROR);
        }
    }

    /**
     * The handleResponse helper method will evaluate the HTTP status
     * code of the Response entity we get from the partner API. Based on
     * the status code received, our API provides the appropriate response to
     * our end-user client.
     * @param responseEntity
     * @return
     */
    private ResponseEntity handleResponse(ResponseEntity responseEntity) {
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            log.info(THIRD_PARTY_API_LOG, responseEntity.getBody());
            return ResponseEntity.ok(responseEntity.getBody().toString());
        } else if (responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value()) {
            log.error(THIRD_PARTY_API_LOG, responseEntity.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEntity.getBody().toString());
        } else if (responseEntity.getStatusCodeValue() == HttpStatus.NOT_FOUND.value()) {
            log.error(THIRD_PARTY_API_LOG, responseEntity.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseEntity.getBody().toString());
        } else {
            log.error(THIRD_PARTY_API_LOG, responseEntity.toString());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseEntity.getBody().toString());
        }
    }

}

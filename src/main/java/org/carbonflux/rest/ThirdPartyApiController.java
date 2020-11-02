package org.carbonflux.rest;

import org.carbonflux.rest.service.ThirdPartyApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This ThirdPartyApiController is used to communicate with API outside our company usually
 * our business partner to deliver added functionality to applications we provide our common customer.
 */
@RestController
// context path below is configurable from application.properties otherwise, defaults to /weather/api
@RequestMapping(path = "${api.context.path:/weather/api}", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThirdPartyApiController {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyApiController.class);
    private static final String API_CONTROLLER_LOG = "{\"pfai-weather-api-controller\": \"{}\"}";
    private ThirdPartyApiService thirdPartyApiService;
    static final String WEATHER_PATH = "/weather/{lat}/{lon}";

    public ThirdPartyApiController(ThirdPartyApiService thirdPartyApiService) {
        this.thirdPartyApiService = thirdPartyApiService;
    }

    @GetMapping(path = WEATHER_PATH)
    public ResponseEntity<String> getWeather(@PathVariable Double lat, @PathVariable Double lon) {
        ResponseEntity<String> responseEntity;
        log.info("{\"method\": \"getWeather\"}");
        log.info("{\"lat\": {}, \"long\": {}}", lat, lon);
        try {
            responseEntity = thirdPartyApiService.getWeather(lat, lon);
        } catch (Exception e) {
            log.error(API_CONTROLLER_LOG, e.getMessage());
            return ResponseEntity.unprocessableEntity().body("error calling 3rd-party app");
        }
        log.info(API_CONTROLLER_LOG, responseEntity);
        return responseEntity;
    }
}

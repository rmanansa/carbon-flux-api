package com.noodle.pfai.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noodle.pfai.rest.service.UserEndpointService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * The UserEndpointController class is used as Controller for all the API
 * endpoints that a client that has a role of USER_ROLE can access.
 * It uses an injected JDBCTemplate for accessing Database function.
 */
@RestController
// context path below is configurable from application.properties otherwise, defaults to /weather/api
@RequestMapping(path = "${api.context.path:/weather/api}", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpointController {


   private static final Logger log = LoggerFactory.getLogger(UserEndpointController.class);
   private static final String API_CONTROLLER_LOG = "{\"pfai-weather-api-controller\": \"{}\"}";
   private UserEndpointService userEndpointService;
   private Gson gson;
   static final String FILTER_KEY = "i_filter";
   static final String HEALTH_PATH = "/__health";
   static final String ITEM_PATH = "/item";

   /**
    * UserEndpointController is used here to initialize DB schema value
    * pulled from a configuration file.
    */
   public UserEndpointController(UserEndpointService userEndpointService,
                                 @Value("${json.pretty.print}") boolean prettyJSON) {
      this.userEndpointService = userEndpointService;
      // this will send across the wire JSON fields with null values. i.e. {"first_name": null}
      if (prettyJSON) {
         this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
      } else {
         this.gson = new GsonBuilder().serializeNulls().create();
      }
   }

   /**
    * The getHealthCheck method here is used to provide an endpoint
    * for application heart beat.
    *
    * @return - JSON that has a message UP to indicate that app is running.
    */
   @GetMapping(HEALTH_PATH)
   public ResponseEntity<String> getHealthCheck() {
      String msg = "{\"status\": \"UP\"}";
      log.info(API_CONTROLLER_LOG, msg);
      return ResponseEntity.ok(msg);
   }

   /**
    * This coilAggregateByParam method would return JSON data for the
    * new sequence page. It accepts JSON body request as parameter.
    *
    * @param payload - JSON
    * @return - JSON of coil aggregate data
    */
   @PostMapping(path = ITEM_PATH,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<String> itemByParam(@RequestBody Map<String, ?> payload) {
      log.info(API_CONTROLLER_LOG, "item");
      String jsonInput = gson.toJson(payload);
      log.info(API_CONTROLLER_LOG, jsonInput);
      Map<String, Object> dbInputMap = new HashMap();
      dbInputMap.put(FILTER_KEY, payload);
      String jsonResp = null;
      try {
         jsonResp = userEndpointService.getItem(dbInputMap);
      } catch (Exception e) {
         log.error(API_CONTROLLER_LOG, e.getMessage());
         return ResponseEntity.badRequest().body("{\"error\": \"Unable to service coil_aggregate request\"}");
      }
      log.info(API_CONTROLLER_LOG, jsonResp);
      return ResponseEntity.ok(jsonResp);

   }

}

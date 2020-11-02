package org.carbonflux.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;

@RestController
// context path below is configurable from application.properties otherwise, defaults to /weather/api
@RequestMapping(path = "${admin.api.context.path:/weather/api}")
public class AdminEndpointController {

   private static final Logger log = LoggerFactory.getLogger(AdminEndpointController.class);
   private static final String API_CONTROLLER_LOG = "{\"pfai-weather-api-controller\": \"{}\"}";

   static final String SECRETTASK_PATH = "/secrettask";
   @Autowired
   private JdbcTemplate jdbcTemplate;
   private SimpleJdbcCall simpleJdbcCallFunction;

   @GetMapping(path = SECRETTASK_PATH)
   public ResponseEntity<AdminMessage> secretTask() {
      String msg = "{\"msg\": \"this admintask function should only work for users with admin role\"}";
      log.info(API_CONTROLLER_LOG, msg);
      return ResponseEntity.ok(new AdminMessage("Secret task function for admin only!"));
   }

   private static class AdminMessage {

      private final String message;

      private AdminMessage(String message) {
         this.message = message;
      }

      public String getMessage() {
         return message;
      }
   }

}

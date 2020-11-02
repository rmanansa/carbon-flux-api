package org.carbonflux.rest.service;

import org.carbonflux.rest.dal.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Map;

@Service
@Transactional
public class UserEndpointService {
   private static final Logger log = LoggerFactory.getLogger(UserEndpointService.class);
   private static final String API_SERVICE_LOG = "{\"pfai-weather-api-service\": \"{}\"}";
   private static final String DB_JSOUN_OUT_PARAMETER = "v_result";
   private Dao dao;
   static final String FUNC_GET_ITEM_BY_PARAM = "f_get_item_by_parameter";
   static final String FILTER_KEY = "i_filter";

   public UserEndpointService(Dao dao) {
      this.dao = dao;
   }

   public String getItem(Map<String, Object> dbInput) {
      String respJson;
      SqlParameter[] inOutParams = new SqlParameter[] {
              new SqlOutParameter(DB_JSOUN_OUT_PARAMETER, Types.OTHER),
              new SqlParameter(FILTER_KEY, Types.OTHER)
      };
      try {
         respJson = dao.callDBFunction(FUNC_GET_ITEM_BY_PARAM, inOutParams, dbInput);
      } catch (Exception e) {
         log.error(API_SERVICE_LOG, e.getMessage());
         return ResponseEntity.badRequest().body("Can't complete your request").toString();
      }
      return respJson;
   }
 }

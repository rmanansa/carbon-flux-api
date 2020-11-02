package org.carbonflux.rest;

import com.google.gson.Gson;
import org.carbonflux.rest.service.UserEndpointService;
import org.carbonflux.util.AbstractRestControllerTest;
import org.carbonflux.util.LogInUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserEndpointControllerTest extends AbstractRestControllerTest {
   private static final String SERVICE = "service";
   private static final String weather = "weather";
   private static final String API = "api";
   private static final String BEARER = "Bearer ";
   private static final String AUTHORIZATION = "Authorization";
   private static final String USER = "user";
   private static final String PASSWORD = "password";
   @Value("${api.context.path:/weather/api}") String url;

   @MockBean
   private UserEndpointService userEndpointService;

   @Test
   public void postItem() throws Exception {
      final String token = LogInUtils.getTokenForLogin(USER, PASSWORD, getMockMvc());
      Map<String, Object> parentObj = new HashMap<>();
      Map<String, Object> childObj = new HashMap<>();
      childObj.put(API, weather);
      parentObj.put(SERVICE, childObj);
      Gson gson = new Gson();
      String jsonStr = gson.toJson(parentObj);
      Map<String, Object> serviceMap = new HashMap<>();
      serviceMap.put(UserEndpointController.FILTER_KEY, parentObj);
      when(userEndpointService.getItem(serviceMap))
         .thenReturn(jsonStr);

      getMockMvc().perform(MockMvcRequestBuilders.post(url + UserEndpointController.ITEM_PATH)
         .accept(MediaType.APPLICATION_JSON)
         .content(jsonStr)
         .contentType(MediaType.APPLICATION_JSON)
         .header(AUTHORIZATION, BEARER + token))
         .andExpect(status().isOk());
   }

   @Test
   public void getPersonForUser() throws Exception {
      final String token = LogInUtils.getTokenForLogin(USER, PASSWORD, getMockMvc());

      assertSuccessfulPersonRequest(token);
   }

   @Test
   public void getPersonForAdmin() throws Exception {
      final String token = LogInUtils.getTokenForLogin("admin", "admin", getMockMvc());

      assertSuccessfulPersonRequest(token);
   }

   @Test
   public void getPersonForAnonymous() throws Exception {
      getMockMvc().perform(MockMvcRequestBuilders.get(url + UserEndpointController.ITEM_PATH)
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }

   private void assertSuccessfulPersonRequest(String token) throws Exception {
      getMockMvc().perform(MockMvcRequestBuilders.get(url + UserEndpointController.HEALTH_PATH)
         .contentType(MediaType.APPLICATION_JSON)
         .header(AUTHORIZATION, BEARER + token))
         .andExpect(status().isOk())
         .andExpect(content().json("{\"status\": \"UP\"}"));
   }
}

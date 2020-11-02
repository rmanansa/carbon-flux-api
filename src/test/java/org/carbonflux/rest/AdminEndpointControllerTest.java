package org.carbonflux.rest;

import org.carbonflux.util.AbstractRestControllerTest;
import org.carbonflux.util.LogInUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminEndpointControllerTest extends AbstractRestControllerTest {

   @Value("${admin.api.context.path:/weather/api}") String url;

   @Test
   public void getAdminProtectedGreetingForUser() throws Exception {
      final String token = LogInUtils.getTokenForLogin("user", "password", getMockMvc());

      getMockMvc().perform(MockMvcRequestBuilders.get(url + AdminEndpointController.SECRETTASK_PATH)
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isForbidden());
   }

   @Test
   public void getAdminProtectedGreetingForAdmin() throws Exception {
      final String token = LogInUtils.getTokenForLogin("admin", "admin", getMockMvc());

      getMockMvc().perform(MockMvcRequestBuilders.get(url + AdminEndpointController.SECRETTASK_PATH)
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk())
         .andExpect(content().json(
            "{\n" +
               "  \"message\" : \"Secret task function for admin only!\"\n" +
               "}"
         ));
   }

   @Test
   public void getAdminProtectedGreetingForAnonymous() throws Exception {
      getMockMvc().perform(MockMvcRequestBuilders.get(url + AdminEndpointController.SECRETTASK_PATH)
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }
}

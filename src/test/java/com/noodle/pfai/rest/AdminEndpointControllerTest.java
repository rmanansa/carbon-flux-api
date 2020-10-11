package com.noodle.pfai.rest;

import com.noodle.pfai.util.AbstractRestControllerTest;
import com.noodle.pfai.util.LogInUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static com.noodle.pfai.rest.AdminEndpointController.SECRETTASK_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminEndpointControllerTest extends AbstractRestControllerTest {

   @Value("${admin.api.context.path:/weather/api}") String url;

   @Test
   public void getAdminProtectedGreetingForUser() throws Exception {
      final String token = LogInUtils.getTokenForLogin("user", "password", getMockMvc());

      getMockMvc().perform(get(url + SECRETTASK_PATH)
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isForbidden());
   }

   @Test
   public void getAdminProtectedGreetingForAdmin() throws Exception {
      final String token = LogInUtils.getTokenForLogin("admin", "admin", getMockMvc());

      getMockMvc().perform(get(url + SECRETTASK_PATH)
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
      getMockMvc().perform(get(url + SECRETTASK_PATH)
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }
}

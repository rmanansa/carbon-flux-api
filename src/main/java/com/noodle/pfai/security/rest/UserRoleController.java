package com.noodle.pfai.security.rest;

import com.noodle.pfai.security.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.noodle.pfai.security.service.UserService;

@RestController
// context path below is configurable from application.properties otherwise, defaults to /weather/api
@RequestMapping("${api.context.path:/weather/api}")
public class UserRoleController {

   private final UserService userService;

   public UserRoleController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/user")
   public ResponseEntity<Users> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }
}

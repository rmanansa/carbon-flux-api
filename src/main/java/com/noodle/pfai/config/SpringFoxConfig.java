package com.noodle.pfai.config;

import com.google.common.collect.Lists;
import com.noodle.pfai.rest.UserEndpointController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.swagger.models.auth.In.HEADER;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * SpringFoxConfig is the class used to setup the form of Swagger documentation of the API as well as its
 * behavior in providing the User Interface in testing the API endpoints.
 */

@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
@Configuration
public class SpringFoxConfig {

   @Bean
   public Docket actuatorApi() {
      return new Docket(DocumentationType.SWAGGER_2)
         .select()
         .apis(RequestHandlerSelectors.any())
         .paths(PathSelectors.ant( "/**"))
         .build()
         //.securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
         //.securityContexts(singletonList(securityContext()))
         .apiInfo(apiInfo());
   }

   private ApiKey apiKey() {
      return new ApiKey(AUTHORIZATION, "api_key", "header");
   }

   /**
    * This provides the Swagger UI to use the Authorize button to carry the JWT token for all the endpoints.
    * @return SecurityContext
    */
   private SecurityContext securityContext() {
      return SecurityContext.builder()
         .securityReferences(
            singletonList(SecurityReference.builder()
               .reference("JWT")
               .scopes(new AuthorizationScope[0]).build()
            )
         ).build();
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
         .title("weather API")
         .contact(new Contact("CarbonFlux","https://carbonflux.org/","ramil.manansala@noodle.ai"))
         .version("1.0.0")
         .description("RESTful API Secured by JWT")
         .build();
   }

}

package org.carbonflux.config;

import org.carbonflux.security.JwtAccessDeniedHandler;
import org.carbonflux.security.JwtAuthenticationEntryPoint;
import org.carbonflux.security.jwt.JWTConfigurer;
import org.carbonflux.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Order(100)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
   @Value("${api.context.path:/weather/api}") private String contextPath;
   @Value("${admin.api.context.path:/weather/api}") private String adminContextPath;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   // Configure BCrypt password encoder =====================================================================

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configure paths and requests that should be ignored by Spring Security ================================

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/h2-console/**",
            "/v2/api-docs/**",
            "/webjars/springfox-swagger-ui/**",
            "/swagger-resources/**"
         );
   }

   // Configure security settings ===========================================================================

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      //TODO: temporarily block the authorization for DT team
 // <<disable auth>> We need this when disabling JWT Token auth and Spring authz
      httpSecurity
         .csrf().disable()
         .authorizeRequests()
         .antMatchers("/**").permitAll();
 // <<disable auth/>>

// <<JWT Token Auth and Authz>>
/*      httpSecurity
         // we don't need CSRF because our token is invulnerable
         .csrf().disable()

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(authenticationErrorHandler)
         .accessDeniedHandler(jwtAccessDeniedHandler)

         // enable h2-console
         .and()
         .headers()
         .frameOptions()
         .sameOrigin()

         // create no session
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         .and()
         .authorizeRequests()
         .antMatchers(contextPath + "/authenticate").permitAll()
         .antMatchers(contextPath + "/__health").permitAll()
         .antMatchers(contextPath + "/item").hasAuthority("ROLE_USER")
         .antMatchers(adminContextPath + "/secrettask").hasAuthority("ROLE_ADMIN")

         .anyRequest().authenticated()
         .and()
         .apply(securityConfigurerAdapter());*/
      // <<JWT Token Auth and Authz/>>
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }

   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder builder) {
      // Do any additional configuration here
      return builder.build();
   }
}

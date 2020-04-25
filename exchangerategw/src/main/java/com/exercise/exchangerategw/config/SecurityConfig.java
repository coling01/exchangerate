package com.exercise.exchangerategw.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${application.authentication.enabled:true}")
  private boolean authenticationEnabled;

  @Value("${application.authentication.user}")
  private String user;

  @Value("${application.authentication.password}")
  private String password;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (authenticationEnabled) {
      log.info("Authentication ENABLED");
//      http
//        .csrf().disable()
//        .authorizeRequests().anyRequest().authenticated()
//        .and()
//        .httpBasic();
      http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/spec-api.yaml").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
    } else {
      log.warn("Authentication DISABLED");
    }
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser(user)
      .password("{noop}" + password)
      .roles("USER");
  }

}

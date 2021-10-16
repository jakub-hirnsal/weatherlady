package com.sda.weatherlady.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/form").hasAnyRole("SUPER_ADMIN")
                .antMatchers("/web/**").hasAnyRole("ADMIN", "USER", "SUPER_ADMIN")
                //.antMatchers("/web/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_SUPER_ADMIN") // equal to the previous line
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll() // anybody can access to the rest APIs (/conditions)
            .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();





//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("{bcrypt}$2a$10$w6AoSvH7ezHwQomG19OjOev3AZuu/UWkROR5CjW1fXZjvP/dy9mEq").roles("ADMIN")
                .and()
                .withUser("user2").password("{MD5}{WtFrETqej9qZsjAWIlXhM03PxD5xKvdcpr3T9R/BHl8=}2aff96e65139a682af29072ffcef19d1").roles("SUPER_ADMIN")
                .and()
                .withUser("user3").password("{noop}heslo").roles("USER");
    }
}

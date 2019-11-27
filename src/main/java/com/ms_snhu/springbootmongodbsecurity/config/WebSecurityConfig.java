/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms_snhu.springbootmongodbsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ms_snhu.springbootmongodbsecurity.service.CustomUserDetailsService;

/**
 *
 * @author didin
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${webSecurity.adminRoleName}")
    private String adminRole;
    @Value("${webSecurity.userRoleName}")
    private String userRole;
    @Value("${webSecurity.restRoleName}")
    private String restRole;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/pullAll").permitAll()
                .antMatchers("/ticker/**").permitAll()
                .antMatchers("/company/**").permitAll()
                .antMatchers("/topFive/**").permitAll()
                .antMatchers("/delete/**").hasAnyAuthority(adminRole,restRole)
                .antMatchers("/Update/**").hasAnyAuthority(adminRole,restRole)
                .antMatchers("/Insert/**").hasAnyAuthority(adminRole,restRole)
                .antMatchers("/UploadFile/**").hasAnyAuthority(adminRole,restRole)
                .antMatchers("/InsertMultiple/**").hasAnyAuthority(restRole)
                .antMatchers("/UpdateVolume/**").hasAnyAuthority(restRole)
                .antMatchers("/userView/**").hasAuthority(userRole)
                .antMatchers("/sectorOutstandingShares").hasAnyAuthority(userRole,adminRole)
                .antMatchers("/dashboard/**").hasAuthority(adminRole)
                .antMatchers("/saveUserChange/**").hasAuthority(adminRole)
                .antMatchers("/userAdmin/**").hasAuthority(adminRole).anyRequest()
                .authenticated().and().csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler)
                .loginPage("/login").failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}

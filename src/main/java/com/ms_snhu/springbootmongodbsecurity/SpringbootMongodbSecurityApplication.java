package com.ms_snhu.springbootmongodbsecurity;

import com.ms_snhu.springbootmongodbsecurity.domain.Role;
import com.ms_snhu.springbootmongodbsecurity.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootMongodbSecurityApplication {
  
  @Value("${webSecurity.adminRoleName}")
  private String adminRoleName;
  @Value("${webSecurity.userRoleName}")
  private String userRoleName;
  @Value("${webSecurity.restRoleName}")
  private String restRoleName;

  public static void main(String[] args) {
    SpringApplication.run(SpringbootMongodbSecurityApplication.class, args);
  }

  @Bean
  CommandLineRunner init(RoleRepository roleRepository) {

    return args -> {

      Role adminRole = roleRepository.findByRole(adminRoleName);
      if (adminRole == null) {
        Role newAdminRole = new Role();
        newAdminRole.setRole(adminRoleName);
        roleRepository.save(newAdminRole);
      }

      Role userRole = roleRepository.findByRole(adminRoleName);
      if (userRole == null) {
        Role newUserRole = new Role();
        newUserRole.setRole(adminRoleName);
        roleRepository.save(newUserRole);
      }

      Role restRole = roleRepository.findByRole(restRoleName);
      if (restRole == null) {
        Role newRestRole = new Role();
        newRestRole.setRole(restRoleName);
        roleRepository.save(newRestRole);
      }
    };

  }
}

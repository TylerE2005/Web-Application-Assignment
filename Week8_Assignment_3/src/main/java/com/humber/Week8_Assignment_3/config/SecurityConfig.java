package com.humber.Week8_Assignment_3.config;

import com.humber.Week8_Assignment_3.services.ItemUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ItemUserService itemUserService;
    public SecurityConfig(ItemUserService itemUserService) { this.itemUserService = itemUserService; }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                //Decides who can access what page
                        authorizeHttpRequests( (authorize) -> authorize
                        .requestMatchers("/login/**", "register/**").permitAll() //Allows anybody access to these pages
                        .requestMatchers("/store/home").hasAnyRole("ADMIN", "EMPLOYEE", "REGULAR") //Allow users with Admin, Employee and Regular Roles
                        .requestMatchers("/store/warehouse/**").hasAnyRole("ADMIN", "EMPLOYEE") //Allows Users with Admin and Employee roles
                        .requestMatchers("/store/admin/**").hasRole("ADMIN") //Allow Admins are allowed Access
                        .anyRequest().authenticated()
                ).formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login") //brings user to custom login page
                            .defaultSuccessUrl("/store/home") //redirects to store home page after login
                            .permitAll(); //allows everyone to access to login page
                })
                .logout(logout -> logout
                        .logoutUrl("/logout") //brings user to custom logout page
                        .permitAll() //allows access to everybody
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //Creates instance of DaoAuthenticationProvider
        provider.setUserDetailsService(itemUserService); //Allows retrieval of user details from User Database
        provider.setPasswordEncoder(passwordEncoder()); //Allows verification of hashed passwords

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); } //Password Encoding using BCrypt

    @Bean
    public WebSecurityCustomizer ignoreResources(){
        return (webSecurity) -> webSecurity
                .ignoring()
                .requestMatchers("/css/**", "/h2-console/**");
        //Ignores security for static resources and the H2 db console
    }
}

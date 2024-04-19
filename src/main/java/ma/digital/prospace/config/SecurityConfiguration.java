package ma.digital.prospace.config;


import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;


@Configuration

public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.applyPermitDefaultValues();
                    corsConfig.addAllowedMethod("DELETE");
                    corsConfig.addAllowedMethod("PUT");
                    corsConfig.addAllowedMethod("GET");
                    corsConfig.addAllowedMethod("POST");
                    corsConfig.setMaxAge(3600L);
                    cors.configurationSource(request -> corsConfig);
                })
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());

        return http.build();
    }

}
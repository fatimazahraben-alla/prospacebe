package ma.digital.prospace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable()) // Désactive CORS
                .csrf(csrf -> csrf.disable()) // Désactive CSRF
                .authorizeRequests(authz -> authz.anyRequest().permitAll()); // Autorise toutes les requêtes

        return http.build();
    }
}

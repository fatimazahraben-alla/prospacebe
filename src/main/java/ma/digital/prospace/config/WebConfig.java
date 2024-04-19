package ma.digital.prospace.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Cache-Control", "Connection", "Content-Type", "Date",
                        "Expires", "Pragma", "Transfer-Encoding", "Vary", "X-Content-Type-Options", "X-Frame-Options", "X-XSS-Protection")
                .allowCredentials(false)
                .maxAge(1800); // ou maxAge(3600) si vous souhaitez mettre en cache la réponse CORS
    }
}

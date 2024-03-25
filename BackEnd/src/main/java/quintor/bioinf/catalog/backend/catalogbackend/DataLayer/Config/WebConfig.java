package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * This method is used to configure CORS settings for the application.
     * Because the frontend is running on localhost:3000, we need to allow requests from that origin.
     * @param registry The CORS registry to configure
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

}

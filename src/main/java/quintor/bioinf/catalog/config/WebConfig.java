package quintor.bioinf.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is used to configure the Web settings of the application.
 * See the method for more information.
 */
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

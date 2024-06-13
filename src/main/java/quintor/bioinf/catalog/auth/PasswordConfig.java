package quintor.bioinf.catalog.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is repsonsible for configuring the password decoder.
 * The password decoder is used to decode a password from the database
 */
@Configuration
public class PasswordConfig {

    /**
     * Method that creates and returns a new password encoder object.
     * The password encoder is BCryptPasswordEncoder.
     *
     * @return a new password encoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

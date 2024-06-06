package quintor.bioinf.catalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.repository.UserRepository;

/**
 * UserService class that handles the retrieving user data from the database
 * The loadUserByUsername method is used by the Spring Security framework to retrieve the user data from the database
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method that retrieves the user data from the database
     * It calls the findByEmail method from the UserRepository class
     * If the user is not found, a BadCredentialsException is thrown.
     * This bad credentials exception is caught by the exception handling and
     * sends an object back to the client
     * @return UserDetailsService
     */
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new BadCredentialsException("Gebruiker niet gevonden"));
            }
        };
    }
}

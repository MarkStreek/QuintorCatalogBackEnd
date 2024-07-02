package quintor.bioinf.catalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.model.LoginRequest;
import quintor.bioinf.catalog.model.LoginResponse;
import quintor.bioinf.catalog.repository.UserRepository;

/**
 * Service class for handling the authentication process.
 * It communicates with the UserRepository, UserService, PasswordEncoder, JwtService and AuthenticationManager.
 * The signing method is used to authenticate a user and generate a JWT token.
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method that authenticates a user and generates a JWT token.
     * It uses the AuthenticationManager to authenticate the user.
     * If the user is authenticated, the method generates a JWT token using the JwtService.
     * The method returns a LoginResponse object containing the user's email and the JWT token.
     *
     * @param request the LoginRequest object containing the user's email and password
     * @return a LoginResponse object containing the user's email and the JWT token
     */
    public LoginResponse signin(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        return new LoginResponse(user.getEmail(), jwt);
    }
}

package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.model.LoginRequest;
import quintor.bioinf.catalog.model.LoginResponse;
import quintor.bioinf.catalog.services.AuthenticationService;

/**
 * Controller class that handles all incoming requests for the authentication.
 * There is 1 endpoint in this controller where a user can log in.
 * The provided username and password are checked and if valid,
 * a JWT token is generated and returned.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * POST endpoint to log in a user.
     * The incoming request is a LoginRequest object. The object contains the username and password.
     * The service class is called with the incoming LoginRequest object.
     * @param request The incoming request object (Username and password)
     * @param httpRequest The incoming request (used for logging)
     * @return LoginResponse with the JWT token or an error message
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Logging.logIncomingRequest(httpRequest);
        return authenticationService.signin(request);
    }

}

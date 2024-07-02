package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.model.LoginRequest;
import quintor.bioinf.catalog.model.LoginResponse;
import quintor.bioinf.catalog.model.ReturnMessage;
import quintor.bioinf.catalog.services.AuthenticationService;
import quintor.bioinf.catalog.services.JwtService;
import quintor.bioinf.catalog.services.UserService;

import java.util.Date;

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
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationService authenticationService,
                          JwtService jwtService,
                          UserService userService)
    {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userService = userService;
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

    /**
     * POST endpoint to validate a JWT token. This endpoint is used to check if the token is still valid.
     * The incoming request is a JWT token. The token is extracted from the request header and
     * checked by the Jwt service class.
     */
    @PostMapping("/validate")
    public ReturnMessage validateToken(HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        final String authHeader = request.getHeader("Authorization");

        return jwtService.validate(authHeader);
    }
}

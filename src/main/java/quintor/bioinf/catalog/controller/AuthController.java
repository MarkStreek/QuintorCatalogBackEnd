package quintor.bioinf.catalog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.model.LoginRequest;
import quintor.bioinf.catalog.model.LoginResponse;
import quintor.bioinf.catalog.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.signin(request);
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasRole('ROLE_CTO')")
    public String test2() {
        return "Test2";
    }
}

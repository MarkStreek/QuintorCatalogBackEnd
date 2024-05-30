package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.auth.JwtUtil;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.model.LoginRequest;
import quintor.bioinf.catalog.model.LoginResponse;

@Controller
@RequestMapping("/rest/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest loginReq, HttpServletRequest request)  {

        Logging.logIncomingRequest(request);

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

        String email = authentication.getName();
        User user = new User();
        user.setEmail(email);
        String token = jwtUtil.createToken(user);

        return new LoginResponse(email,token);


    }
}

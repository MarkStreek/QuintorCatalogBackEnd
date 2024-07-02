package quintor.bioinf.catalog.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.model.ReturnMessage;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This service class is responsible for all the JWT token related operations.
 * That means generating a token, validating a token, extracting the username from a token, etc.
 * The service class is used in the JwtAuthenticationFilter class and called by the AuthController class.
 *
 * @see quintor.bioinf.catalog.auth.JwtAuthenticationFilter
 * @see quintor.bioinf.catalog.controller.AuthController
 */
@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private final UserService userService;
    /**
     * The secret key is used to sign the JWT token. The key is generated using the Keys class.
     * The key is generated using the HS256 algorithm and is used to verify the token.
     * The expiration time of the token is set to 2 hours. Could easily be changed to a different value.
     */
    SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    Long jwtExpirationMs = 3600000L * 2;

    @Autowired
    public JwtService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method that extracts the username from the token.
     *
     * @param token the token from which the username is extracted
     * @return the username that is extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Method that generates a JWT token.
     * It calls the generateToken method with an empty map and the user details object.
     * The token is generated using the Jwts builder.
     * The signing and expiration time are used from the class variables.
     *
     * @param userDetails the user details object that is used to generate the token
     * @return the generated token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Method that validates a token.
     * It checks if the username in the token is the same as the username in the user details object.
     * And it checks if the token is not expired.
     *
     * @param token the token that is validated
     * @param userDetails the user details object that is used to validate the token
     * @return true if the token is valid, false if the token is not valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Generic method that extracts a claim from the token using a claims' resolver.
     * The claims' resolver is a function that is passed as a parameter.
     * The function is applied to the claims object and the result is returned.
     *
     * @param token the token from which the claim is extracted
     * @param claimsResolvers the function that is applied to the claims object
     * @return the result of the function that is applied to the claims object
     * @param <T> the type of the result that is returned
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * The actual method that generates the token using a builder pattern.
     * The claims are set, the subject is set, the expiration time is set
     * and the token is signed with the secret key.
     *
     * @param extraClaims the extra claims that are added to the token
     * @param userDetails the user details object that is used to generate the token
     * @return the generated token
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(this.jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Simple method that checks if the token is expired. It calls the extractExpiration method,
     * If the expiration date is before the current date, the token is expired.
     *
     * @param token the token that is checked if it is expired
     * @return true if the token is expired, false if the token is not expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Method that retrieves the expiration date from the token and returns the data extracted.
     * Therefore, it calls the extractClaim method with the expiration date as a parameter.
     *
     * @param token the token from which the expiration date is extracted
     * @return the expiration date that is extracted from the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Method that extracts all claims from the token.
     * It uses the Jwts parser builder to parse the token and extract the claims.
     *
     * @param token the token from which the claims are extracted
     * @return the claims that are extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Method that validates a token. THe method is called by a AuthController endpoint.
     * The method checks if the token is present and if the token starts with "Bearer ".
     * If the token is present, the token is extracted and the username is extracted from the token.
     * If the username is not empty, the user details are loaded from the user service.
     * A ReturnMessage object is returned with the right status code and message.
     *
     * @param authHeader the header of the incoming request
     * @return a ReturnMessage object with the status code and message
     */
    public ReturnMessage validate(String authHeader) {
        if (authHeader != null || authHeader.startsWith("Bearer ")) {

            final String jwt = authHeader.substring(7);
            final String userEmail = extractUsername(jwt);

            if (userEmail != null && !userEmail.isEmpty()) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
                if (isTokenValid(jwt, userDetails)) {
                    return new ReturnMessage(
                            HttpStatus.OK.value(),
                            new Date(),
                            "Token is valid",
                            "Token is still allowed to be used for incoming requests");
                }
            } else log.error("User email is empty or null While validating a token");
        }
        return new ReturnMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                "Token is invalid",
                "Token is not valid anymore, please log in again");
    }
}

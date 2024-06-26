package quintor.bioinf.catalog.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import quintor.bioinf.catalog.services.JwtService;
import quintor.bioinf.catalog.services.UserService;

import java.io.IOException;


/**
 * This class is responsible for filtering the incoming requests and checking if the JWT token is valid.
 * The filter is applied to requests of url's that are specified in the SecurityConfig.java
 * The methods in this class are responsible for checking the presence of the bearer token in the request header,
 * and if the token is valid, it will set the authentication context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * This method is responsible for filtering the incoming requests and checking if the JWT token is valid.
     * The header of the incoming request is defined. The checking of the presence of a token is being done in another method.
     * If the token is present, the token is being extracted and the user email is being extracted from the token.
     * If the user email is not empty and the security context is not set, the user details are being loaded from the user service.
     * If the token is valid, the security context is being set.
     * 
     * @param request the incoming request
     * @param response the response that will be sent back
     * @param filterChain the filter chain that will be executed
     * @throws ServletException if the request could not be handled
     * @throws IOException if the request could not be handled
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        // Check for the bearer presence, if it is not present, continue with the filter chain
        String requestUri = request.getRequestURI();
        if (requestUri != null && requestUri.equals("/auth/validate")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (checkForBearerPresence(request, response, filterChain, authHeader)) return;

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && !userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method is responsible for checking the presence of the bearer token in the request header.
     * If the token is not present, return true and continue with the filter chain.
     * 
     * @param request the incoming request
     * @param response the response that will be sent back
     * @param filterChain the filter chain that will be executed
     * @param authHeader the header of the incoming request
     * @return true if the token is not present, false if the token is present
     * @throws IOException if the request could not be handled
     * @throws ServletException if the request could not be handled
     */
    private static boolean checkForBearerPresence(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authHeader) throws IOException, ServletException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }
}

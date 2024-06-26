package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple logging class that logs incoming requests.
 * The class has static method(s) that can be called from other classes.
 * This way, the logging can be done in a single place.
 */
public class Logging {

    // The logger object that is used to log the incoming requests
    private static final Logger log = LoggerFactory.getLogger(Logging.class);

    /**
     * Basic static logging method that logs an incoming request.
     * @param request The incoming request
     */
    public static void logIncomingRequest(HttpServletRequest request) {
        log.info("INCOMING REQUEST details: " + "CLIENT IP: {}" + " PORT: {}" + " REQUEST URI: {}" + " METHOD: {}" + " USER AGENT: {}",
                request.getRemoteAddr(),
                request.getRemotePort(),
                request.getRequestURI(),
                request.getMethod(),
                request.getHeader("User-Agent"));
    }
}

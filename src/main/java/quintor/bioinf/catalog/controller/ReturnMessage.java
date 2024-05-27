package quintor.bioinf.catalog.controller;

import java.util.Date;

/**
 * Record that is being used as Return message
 * It contains the status code, timestamp, message and description.
 * This record will be used to create an ReturnMessage object back to the client.
 *
 * @param statusCode status code of the error
 * @param timestamp datetime of the error
 * @param message message of the error
 * @param description description of the error
 */
public record ReturnMessage
        (
            int statusCode,
            Date timestamp,
            String message,
            String description
        ) {

}


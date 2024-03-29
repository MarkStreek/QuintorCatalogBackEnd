package quintor.bioinf.catalog.controller;

import java.util.Date;

/**
 * Record that is being used as Error message
 * It contains the status code, timestamp, message and description.
 * When the ExceptionController encounters an exception, this record
 * will be used to create an ErrorMessage object back to the client.
 *
 * @param statusCode status code of the error
 * @param timestamp datetime of the error
 * @param message message of the error
 * @param description description of the error
 */
public record ErrorMessage
        (
            int statusCode,
            Date timestamp,
            String message,
            String description
        ) {

}


package quintor.bioinf.catalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage exception(Exception ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }
}

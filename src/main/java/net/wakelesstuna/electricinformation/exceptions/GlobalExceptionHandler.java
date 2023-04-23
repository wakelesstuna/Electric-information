package net.wakelesstuna.electricinformation.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SpotPriceException.class)
    public ErrorResponse handleSpotPriceException(final SpotPriceException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).status(ex.getHttpStatus())
                .code(ex.getHttpStatus().value()).build();
    }
}

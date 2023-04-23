package net.wakelesstuna.electricinformation.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SpotPriceException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    public SpotPriceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

package net.wakelesstuna.electricinformation.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorResponse(HttpStatus status, Integer code, String message) {
}

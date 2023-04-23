package net.wakelesstuna.electricinformation.entites.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record PriceResponse(String time, Integer price, String unit) {
}

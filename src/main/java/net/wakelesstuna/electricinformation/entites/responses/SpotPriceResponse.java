package net.wakelesstuna.electricinformation.entites.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotPriceResponse(String date, String unit, String priceArea, List<PriceResponse> prices) {
}

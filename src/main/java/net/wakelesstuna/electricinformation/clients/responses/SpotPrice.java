package net.wakelesstuna.electricinformation.clients.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotPrice(@JsonProperty("TimeStamp") String timeStamp,
                        @JsonProperty("TimeStampDay") LocalDate timeStampDay,
                        @JsonProperty("TimeStampHour") String timeStampHour,
                        @JsonProperty("Value") Integer value,
                        @JsonProperty("PriceArea") String priceArea,
                        @JsonProperty("Unit") String unit) {
}

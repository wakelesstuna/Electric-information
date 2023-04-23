package net.wakelesstuna.electricinformation.clients;

import lombok.RequiredArgsConstructor;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.clients.responses.SpotPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WaterFallClient {

    @Value("${application.waterfall.base-url}")
    private String baseUrl;
    private static final String SPOT_PRICE = "/price/spot/pricearea/%s/%s/%s";

    private final WebClient webClient;

    public List<SpotPrice> getSpotPrices(final String date, final PriceArea priceArea) {
        return webClient.get()
                .uri(baseUrl + SPOT_PRICE.formatted(date, date, priceArea.getCode()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SpotPrice>>() {
                }).block();
    }

}

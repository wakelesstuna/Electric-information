package net.wakelesstuna.electricinformation.services;

import lombok.RequiredArgsConstructor;
import net.wakelesstuna.electricinformation.clients.WaterFallClient;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.clients.responses.SpotPrice;
import net.wakelesstuna.electricinformation.entites.db.SpotPriceEntity;
import net.wakelesstuna.electricinformation.entites.responses.PriceResponse;
import net.wakelesstuna.electricinformation.entites.responses.SpotPriceResponse;
import net.wakelesstuna.electricinformation.exceptions.SpotPriceException;
import net.wakelesstuna.electricinformation.repositories.SpotPriceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotPriceService {

    private final SpotPriceRepository spotPriceRepo;
    private final WaterFallClient waterFallClient;

    public SpotPriceResponse getSpotPrices(final LocalDate date) {
        List<SpotPriceEntity> spotPrices = spotPriceRepo.findAllByTimeStampDay(date);
        if (spotPrices.isEmpty()) {
            throw new SpotPriceException(HttpStatus.NOT_FOUND, "No price data found for date %s.".formatted(date));
        }
        return SpotPriceResponse.builder()
                .date(String.valueOf(date))
                .priceArea(extractPriceArea(spotPrices))
                .unit(extractUnit(spotPrices))
                .prices(mapSpotPrices(spotPrices))
                .build();
    }

    public void populateSpotPrices(final LocalDate date, final PriceArea priceArea) {
        long count = spotPriceRepo.countByTimeStampDay(date);
        if (count > 0) {
            throw new SpotPriceException(HttpStatus.BAD_REQUEST, "Prices for date %s already exists.".formatted(date));
        }
        createSpotPrices(date, priceArea);
    }

    public void createSpotPrices(final LocalDate date, final PriceArea priceArea) {
        List<SpotPrice> response = waterFallClient.getSpotPrices(String.valueOf(date), priceArea);

        List<SpotPriceEntity> spotPrices = response.stream()
                .map(spotPrice -> SpotPriceEntity.builder()
                        .priceArea(spotPrice.priceArea())
                        .value(spotPrice.value())
                        .unit(spotPrice.unit())
                        .timeStampHour(spotPrice.timeStampHour())
                        .timeStampDay(spotPrice.timeStampDay())
                        .build())
                .toList();

        spotPriceRepo.saveAll(spotPrices);
    }

    private String extractUnit(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.isEmpty() ? null : spotPrices.get(0).getUnit();
    }

    private String extractPriceArea(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.isEmpty() ? null : spotPrices.get(0).getPriceArea();
    }

    private List<PriceResponse> mapSpotPrices(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.stream()
                .map(this::mapSpotPrice)
                .toList();
    }

    private PriceResponse mapSpotPrice(final SpotPriceEntity spotPrice) {
        return PriceResponse.builder()
                .time(spotPrice.getTimeStampHour())
                .price(spotPrice.getValue())
                .unit(spotPrice.getUnit())
                .build();
    }
}

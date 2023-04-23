package net.wakelesstuna.electricinformation.services;

import lombok.RequiredArgsConstructor;
import net.wakelesstuna.electricinformation.clients.WaterFallClient;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.clients.responses.SpotPrice;
import net.wakelesstuna.electricinformation.entites.db.SpotPriceEntity;
import net.wakelesstuna.electricinformation.entites.responses.SpotPriceResponse;
import net.wakelesstuna.electricinformation.exceptions.SpotPriceException;
import net.wakelesstuna.electricinformation.helpers.SpotPriceHelper;
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

    public SpotPriceResponse getSpotPrices(final LocalDate date, PriceArea priceArea) {
        List<SpotPriceEntity> spotPrices = spotPriceRepo.findAllByTimeStampDayAndPriceArea(date, priceArea);
        if (spotPrices.isEmpty()) {
            throw new SpotPriceException(HttpStatus.NOT_FOUND, "No price data found for date %s.".formatted(date));
        }
        return SpotPriceResponse.builder()
                .date(String.valueOf(date))
                .priceArea(SpotPriceHelper.extractPriceArea(spotPrices))
                .unit(SpotPriceHelper.extractUnit(spotPrices))
                .prices(SpotPriceHelper.mapSpotPrices(spotPrices))
                .build();
    }

    public void populateSpotPrices(final LocalDate date, final PriceArea priceArea) {
        long count = spotPriceRepo.countByTimeStampDayAndPriceArea(date, priceArea);
        if (count > 0) {
            throw new SpotPriceException(HttpStatus.BAD_REQUEST, "Prices for date %s already exists.".formatted(date));
        }
        createSpotPrices(date, priceArea);
    }

    public void createSpotPrices(final LocalDate date, final PriceArea priceArea) {
        List<SpotPrice> response = waterFallClient.getSpotPrices(String.valueOf(date), priceArea);

        List<SpotPriceEntity> spotPrices = response.stream()
                .map(spotPrice -> SpotPriceEntity.builder()
                        .priceArea(PriceArea.getEnumFromCode(spotPrice.priceArea()))
                        .value(spotPrice.value())
                        .unit(spotPrice.unit())
                        .timeStampHour(spotPrice.timeStampHour())
                        .timeStampDay(spotPrice.timeStampDay())
                        .build())
                .toList();

        spotPriceRepo.saveAll(spotPrices);
    }


}

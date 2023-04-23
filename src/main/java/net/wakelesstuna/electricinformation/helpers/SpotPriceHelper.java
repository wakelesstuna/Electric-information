package net.wakelesstuna.electricinformation.helpers;

import net.wakelesstuna.electricinformation.entites.db.SpotPriceEntity;
import net.wakelesstuna.electricinformation.entites.responses.PriceResponse;

import java.util.List;

public final class SpotPriceHelper {

    private SpotPriceHelper() {
    }

    public static String extractUnit(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.isEmpty() ? null : spotPrices.get(0).getUnit();
    }

    public static String extractPriceArea(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.isEmpty() ? null : spotPrices.get(0).getPriceArea().getCode();
    }

    public static List<PriceResponse> mapSpotPrices(final List<SpotPriceEntity> spotPrices) {
        return spotPrices.stream()
                .map(SpotPriceHelper::mapSpotPrice)
                .toList();
    }

    public static PriceResponse mapSpotPrice(final SpotPriceEntity spotPrice) {
        return PriceResponse.builder()
                .time(spotPrice.getTimeStampHour())
                .price(spotPrice.getValue())
                .unit(spotPrice.getUnit())
                .build();
    }
}

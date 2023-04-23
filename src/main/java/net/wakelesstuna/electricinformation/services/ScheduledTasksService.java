package net.wakelesstuna.electricinformation.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.repositories.SpotPriceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Log4j2
public class ScheduledTasksService {

    @Value("${application.waterfall.sync.price-area}")
    private PriceArea priceArea;

    private final SpotPriceRepository spotPriceRepo;
    private final SpotPriceService spotPriceService;

    @Scheduled(cron = "${application.waterfall.sync.cron}")
    public void syncWaterFallSpotPrices() {
        LocalDate currentDate = LocalDate.now();

        if (spotPriceRepo.countByTimeStampDayAndPriceArea(currentDate, priceArea) > 0) {
            log.info("Spot prices for {} and area {} already synced.", currentDate, priceArea);
            return;
        }

        log.info("Populating spot prices for {} with price area {}", currentDate, priceArea);
        spotPriceService.createSpotPrices(currentDate, priceArea);
        log.info("Spot prices populated for {} with price area {}", currentDate, priceArea);
    }
}

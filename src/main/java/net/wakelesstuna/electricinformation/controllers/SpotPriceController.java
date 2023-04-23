package net.wakelesstuna.electricinformation.controllers;

import lombok.RequiredArgsConstructor;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.entites.responses.SpotPriceResponse;
import net.wakelesstuna.electricinformation.services.SpotPriceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/spot-prices")
@RequiredArgsConstructor
public class SpotPriceController {

    private final SpotPriceService spotPriceService;

    @GetMapping("/{date}")
    public SpotPriceResponse getSpotPrices(@PathVariable final LocalDate date) {
        return spotPriceService.getSpotPrices(date);
    }

    @PostMapping("/{date}")
    @ResponseStatus(CREATED)
    public void populateSpotPrices(@PathVariable final LocalDate date,
                                   @RequestParam final PriceArea priceArea) {
        spotPriceService.populateSpotPrices(date, priceArea);
    }
}

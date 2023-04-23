package net.wakelesstuna.electricinformation.controllers;

import lombok.RequiredArgsConstructor;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;
import net.wakelesstuna.electricinformation.entites.responses.SpotPriceResponse;
import net.wakelesstuna.electricinformation.services.SpotPriceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/spot-prices")
@RequiredArgsConstructor
public class SpotPriceController {

    private final SpotPriceService spotPriceService;

    @GetMapping
    @ResponseStatus(OK)
    public SpotPriceResponse getSpotPrices(@RequestParam final LocalDate date,
                                           @RequestParam final PriceArea priceArea) {
        return spotPriceService.getSpotPrices(date, priceArea);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void populateSpotPrices(@RequestParam final LocalDate date,
                                   @RequestParam final PriceArea priceArea) {
        spotPriceService.populateSpotPrices(date, priceArea);
    }
}

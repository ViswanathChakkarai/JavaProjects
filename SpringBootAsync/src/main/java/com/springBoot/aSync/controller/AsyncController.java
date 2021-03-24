package com.springBoot.aSync.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.aSync.model.Country;
import com.springBoot.aSync.service.CountryClient;


/**
 * App URL for testing: http://localhost:8080/country/freu
 * login credentials: vchakkaraiv@gmail.com / 9091@vcVC!
 * 
 * @author USER
 *
 */


@Component
//@Api(value = "CountryResource")
@RequestMapping("/country")
@RestController
public class AsyncController {

	private final CountryClient countryClient;

    public AsyncController(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    /* @ApiOperation(httpMethod = "GET", value = "Get all European and French speaking countries", response = String.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Countries not found"),
            @ApiResponse(code = 500, message = "The countries could not be fetched")
    }) */
    @GetMapping("/freu")
    public List<String> getAllEuropeanFrenchSpeakingCountries() throws Throwable {
        CompletableFuture<List<Country>> countriesByLanguageFuture = countryClient.getCountriesByLanguage("fr");
        CompletableFuture<List<Country>> countriesByRegionFuture = countryClient.getCountriesByRegion("europe");
        List<String> europeanFrenchSpeakingCountries;
        try {
            europeanFrenchSpeakingCountries = new ArrayList<>(countriesByLanguageFuture.get().stream().map(Country::getName).collect(Collectors.toList()));
            europeanFrenchSpeakingCountries.retainAll(countriesByRegionFuture.get().stream().map(Country::getName).collect(Collectors.toList()));
        } catch (Throwable e) {
            throw e.getCause();
        }
        return europeanFrenchSpeakingCountries;
    }
}


package com.springBoot.aSync.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springBoot.aSync.model.Country;



@Service
public class CountryClient {
	RestTemplate restTemplate = new RestTemplate();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	
	@Async("aSyncPoolExecutor")
	public CompletableFuture<List<Country>> getCountriesByLanguage(String language) {
		//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String url = "https://restcountries.eu/rest/v2/lang/" + language + "?fields=name";
		Country[] response = restTemplate.getForObject(url, Country[].class);
		System.out.println(
				"---By Language--: " + url + "---" + Arrays.asList(response)/* +"---"+sdf.format(timestamp) */);
		return CompletableFuture.completedFuture(Arrays.asList(response));
	}

	@Async("aSyncPoolExecutor")
	public CompletableFuture<List<Country>> getCountriesByRegion(String region) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String url = "https://restcountries.eu/rest/v2/region/" + region + "?fields=name";
		Country[] response = restTemplate.getForObject(url, Country[].class);
		System.out.println("---By region--: "+url+"---"+Arrays.asList(response)+"---"+sdf.format(timestamp));
		return CompletableFuture.completedFuture(Arrays.asList(response));
	}
}
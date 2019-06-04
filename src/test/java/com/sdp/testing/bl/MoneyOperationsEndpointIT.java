package com.sdp.testing.bl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class MoneyOperationsEndpointIT {
    @Autowired
    private TestRestTemplate restTemplate;

    private String MONEY_OPERATIONS_ENDPOINT_URL = "/monetary_operations/v1";

    @Test
    public void convertTest() {

        //given
        String convert = MONEY_OPERATIONS_ENDPOINT_URL + "/convert";

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(convert)
                // Add query parameter
                .queryParam("inputValue", "2")
                .queryParam("inputUnit", "PLN")
                .queryParam("targetUnit", "USD");


        // when
        restTemplate= new TestRestTemplate();
        ResponseEntity<Currency> convertedCurrencyResponse = restTemplate.getForEntity(builder.toUriString(), Currency.class);
        Currency currency = convertedCurrencyResponse.getBody();

        //then
        assertEquals(CurrencyUnit.USD, currency.getCurrencyUnit());
        assertEquals(new BigDecimal(0.52).round(new MathContext(2, RoundingMode.HALF_UP)), currency.getValue());
    }

    @Test
    public void sumTest() {
        //given
        String convert = MONEY_OPERATIONS_ENDPOINT_URL + "/sum";

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(convert)
                // Add query parameter
                .queryParam("firstValue", "2")
                .queryParam("secondValue", "2")
                .queryParam("targetUnit", "USD");


        // when
        ResponseEntity<Currency> convertedCurrencyResponse = restTemplate.getForEntity(builder.toUriString(), Currency.class);
        Currency currency = convertedCurrencyResponse.getBody();

        //then
        assertEquals(CurrencyUnit.USD, currency.getCurrencyUnit());
        assertEquals(new BigDecimal(4).round(new MathContext(2, RoundingMode.HALF_UP)), currency.getValue());
    }
}

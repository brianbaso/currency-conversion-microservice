package com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeignStyle(@PathVariable String from, @PathVariable String to,
                                                            @PathVariable BigDecimal quantity) {

        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

        logger.info("{}", response);

        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }


    /**
     * 1. Wrap parameters inside a map for the RestTemplate().getForEntity() method
     * 2. Get the currency exchange properties from the Forex Microservice
     * 3. Store the body of the responseEntity in a new CurrencyConversionBean
     * 4. Build new bean with the response
     *
     * @param from
     * @param to
     * @param quantity
     * @return
     */
//    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
//    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
//                                                  @PathVariable BigDecimal quantity) {
//
//        // Put the parameters inside a map for the getForEntity() method
//        Map<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("from", from);
//        uriVariables.put("to", to);
//
//        // Retrieve a representation by doing a GET on the URI template
//        // RestTemplate().getForEntity(
//        //  java.lang.String url, java.lang.Class<T> responseType, java.util.Map<java.lang.String,?> uriVariables)
//
//        // Why two CurrencyConversionBean?
//        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
//                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//                CurrencyConversionBean.class, uriVariables);
//
//        // Why create another bean here if we are just going to make one below?
//        if (!(responseEntity.getStatusCode().is2xxSuccessful())) {
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Body Not Found");
//        }
//
//        CurrencyConversionBean response = responseEntity.getBody();
//
//        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(),
//                quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());

//    }
}

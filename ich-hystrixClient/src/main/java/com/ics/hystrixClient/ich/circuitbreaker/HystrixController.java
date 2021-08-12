package com.ics.hystrixClient.ich.circuitbreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
class HystrixController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/circuitSearchRate")
    @HystrixCommand(fallbackMethod = "getDefaultSearchRate",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliSeconds", value = "500")})
    public Object cloudProductList() {
        return restTemplate.getForObject("http://localhost:8080/searchRate?rateId=" + 100, Object.class);
    }

    public ResponseEntity getDefaultSearchRate() {
        ResponseEntity<Surcharge> listResponseEntity = restTemplate.getForEntity("https://surcharge.free.beeceptor.com/surcharge", Surcharge.class);
        Surcharge surcharge = listResponseEntity.getBody();
        return new ResponseEntity(surcharge, HttpStatus.OK);
    }
}

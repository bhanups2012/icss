package com.ics.service;

import com.ics.domain.Rate;
import com.ics.domain.RateAndSurcharge;
import com.ics.domain.Surcharge;
import com.ics.service.exception.InvalidRateException;
import com.ics.service.exception.RateNotFoundException;
import com.ics.service.repository.SurchargeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.ics.service.ConstantUtil.*;

@Service
public class SurchargeRateService {
    private final Logger logger = LoggerFactory.getLogger(SurchargeRateService.class);
    private final String surchargeRateUri;
    private final SurchargeRateRepository surchargeRateRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public SurchargeRateService(SurchargeRateRepository surchargeRateRepository) {
        this.surchargeRateRepository = surchargeRateRepository;
        restTemplate = new RestTemplate();
        surchargeRateUri = SURCHARGE_URI;
    }

    public RateAndSurcharge searchRate(int rateId) throws RateNotFoundException {
        String requestName = "surcharge";
        logger.info("surcharge api call initiated--------------- ");
        ResponseEntity<Surcharge> listResponseEntity = restTemplate.getForEntity(surchargeRateUri + requestName, Surcharge.class);
        logger.info("surcharge api call success----------------- ");
        Surcharge surcharge = listResponseEntity.getBody();
        Rate rate = findByRateId(rateId);
        if (rate == null)
            throw new RateNotFoundException(RATE_ID_NOT_FOUND);
        return RateAndSurcharge.builder()
                               .surcharge(surcharge)
                               .rate(rate)
                               .build();
    }

    public void addRate(Rate rate) throws InvalidRateException {
        Rate rateEntity = findByRateId(rate.getRateId());
        if (rateEntity == null)
            surchargeRateRepository.save(rate);
        else
            throw new InvalidRateException(RATE_ID_EXIST);
    }

    public void updateRate(Rate rate) throws InvalidRateException {
        Rate rateEntity = findByRateId(rate.getRateId());
        if (rateEntity != null)
            surchargeRateRepository.save(rate);
        else
            throw new InvalidRateException(RATE_ID_NOT_FOUND_UPDATE);
    }

    public void deleteRate(int rateId) throws InvalidRateException {
        Rate rateEntity = findByRateId(rateId);
        if (rateEntity != null)
            surchargeRateRepository.deleteById(rateId);
        else
            throw new InvalidRateException(RATE_ID_NOT_FOUND);
    }

    private Rate findByRateId(int rateId) {
        logger.info("DB find RateId from Rate table initiated--------------- ");
        Rate rate = surchargeRateRepository.findByRateId(rateId);
        logger.info("DB find RateId from Rate table success --------------- ");
        return rate;
    }
}

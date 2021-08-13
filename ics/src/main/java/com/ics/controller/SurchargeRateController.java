package com.ics.controller;

import com.ics.controller.validate.RateValidator;
import com.ics.domain.Rate;
import com.ics.domain.RateAndSurcharge;
import com.ics.service.ConstantUtil;
import com.ics.service.SurchargeRateService;
import com.ics.service.exception.InvalidRateException;
import com.ics.service.exception.RateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("rms")
public class SurchargeRateController {
    private final Logger logger = LoggerFactory.getLogger(SurchargeRateController.class);

    private final SurchargeRateService surchargeRateService;
    private final RateValidator rateValidator;

    @Autowired
    public SurchargeRateController(SurchargeRateService surchargeRateService) {
        this.surchargeRateService = surchargeRateService;
        this.rateValidator = new RateValidator();
    }

    @GetMapping("searchRate")
    public ResponseEntity searchRate(@RequestParam int rateId) {
        try {
            logger.info("searchRate api call initiated----" + LocalDateTime.now());
            RateAndSurcharge rateAndSurcharge = surchargeRateService.searchRate(rateId);
            logger.info("searchRate api call success----" + LocalDateTime.now());
            return new ResponseEntity(rateAndSurcharge, HttpStatus.OK);

        } catch (RateNotFoundException e) {
            logger.info("searchRate api call exception :" + e.getMessage() + " , " + LocalDateTime.now());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("addRate")
    public ResponseEntity addRate(@RequestBody Rate rate) {
        try {
            logger.info("addRate api call initiated----" + LocalDateTime.now());
            rateValidator.validateRate(rate);
            surchargeRateService.addRate(rate);
            logger.info("addRate api call success ----" + LocalDateTime.now());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (InvalidRateException e) {
            logger.info("addRate api call exception :" + e.getMessage() + " , " + LocalDateTime.now());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("addRate api error: " + e.getMessage());
            return new ResponseEntity(ConstantUtil.INTERNAL_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateRate")
    public ResponseEntity updateRate(@RequestBody Rate rate) {
        try {
            logger.info("updateRate api call initiated----" + LocalDateTime.now());
            rateValidator.validateRate(rate);
            surchargeRateService.updateRate(rate);
            logger.info("updateRate api call success ----" + LocalDateTime.now());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (InvalidRateException e) {
            logger.info("updateRate api call exception :" + e.getMessage() + " , " + LocalDateTime.now());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("updateRate api error: " + e.getMessage());
            return new ResponseEntity(ConstantUtil.INTERNAL_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteRate")
    public ResponseEntity deleteRate(@RequestParam int rateId) {
        try {
            logger.info("deleteRate api call initiated----" + LocalDateTime.now());
            surchargeRateService.deleteRate(rateId);
            logger.info("deleteRate api call success ----" + LocalDateTime.now());
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (InvalidRateException e) {
            logger.info("deleteRate api call exception :" + e.getMessage() + " , " + LocalDateTime.now());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

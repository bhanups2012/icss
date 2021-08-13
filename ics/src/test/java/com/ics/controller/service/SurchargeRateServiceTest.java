package com.ics.controller.service;

import com.ics.domain.Rate;
import com.ics.domain.RateAndSurcharge;
import com.ics.domain.Surcharge;
import com.ics.service.SurchargeRateService;
import com.ics.service.exception.InvalidRateException;
import com.ics.service.exception.RateNotFoundException;
import com.ics.service.repository.SurchargeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.ics.service.ConstantUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurchargeRateServiceTest {
    protected Rate RATE = Rate.builder()
                              .rateId(100)
                              .rateDescription("dummy")
                              .rateEffectiveDate(LocalDate.now())
                              .rateExpirationDate(LocalDate.now()
                                                           .plusDays(2))
                              .amount(200)
                              .build();
    protected Surcharge SURCHARGE = Surcharge.builder()
                                             .surcharge(200)
                                             .tax(50)
                                             .build();

    @Autowired
    SurchargeRateService surchargeRateService;

    SurchargeRateService surchargeRateService2;

    @Mock
    private SurchargeRateRepository surchargeRateRepository;

    @BeforeEach
    public void setUp() {
        surchargeRateService2 = new SurchargeRateService(surchargeRateRepository);
    }

    @Test
    public void searchRate_givenValidRateId_thenStatus200_WithRateResponse() throws RateNotFoundException {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(RATE);
        RateAndSurcharge rateAndSurcharge = surchargeRateService2.searchRate(100);
        assertEquals(rateAndSurcharge.getRate()
                                     .getRateId(), RATE.getRateId());
        assertEquals(rateAndSurcharge.getRate()
                                     .getAmount(), RATE.getAmount());
        assertEquals(rateAndSurcharge.getRate()
                                     .getRateDescription(), RATE.getRateDescription());
        assertEquals(rateAndSurcharge.getRate()
                                     .getRateEffectiveDate(), RATE.getRateEffectiveDate());
        assertEquals(rateAndSurcharge.getRate()
                                     .getRateExpirationDate(), RATE.getRateExpirationDate());
        assertEquals(rateAndSurcharge.getSurcharge()
                                     .getSurcharge(), SURCHARGE.getSurcharge());
        assertEquals(rateAndSurcharge.getSurcharge()
                                     .getTax(), SURCHARGE.getTax());
    }

    @Test
    public void searchRate_givenValidRateId_thenStatus404_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(null);
        try {
            RateAndSurcharge rateAndSurcharge = surchargeRateService2.searchRate(100);
        } catch (RateNotFoundException e) {
            assertEquals(e.getMessage(), RATE_ID_NOT_FOUND);
        }

    }

    @Test
    public void addRate_givenValidRateId_thenStatus200_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(null);
        when(surchargeRateRepository.save(RATE)).thenReturn(any());
        try {
            surchargeRateService2.addRate(RATE);
        } catch (InvalidRateException e) {
            assertFalse(true);
        }
    }

    @Test
    public void addRate_givenValidRateId_thenStatus404_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(RATE);
        try {
            surchargeRateService2.addRate(RATE);
        } catch (InvalidRateException e) {
            assertEquals(e.getMessage(), RATE_ID_EXIST);
        }
    }

    @Test
    public void updateRate_givenValidRateId_thenStatus200_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(RATE);
        when(surchargeRateRepository.save(RATE)).thenReturn(any());
        try {
            surchargeRateService2.updateRate(RATE);
        } catch (InvalidRateException e) {
            assertFalse(true);
        }
    }

    @Test
    public void updateRate_givenValidRateId_thenStatus404_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(null);
        try {
            surchargeRateService2.updateRate(RATE);
        } catch (InvalidRateException e) {
            assertEquals(e.getMessage(), RATE_ID_NOT_FOUND_UPDATE);
        }
    }

    @Test
    public void deleteRate_givenValidRateId_thenStatus200_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(RATE);
        try {
            surchargeRateService2.deleteRate(100);
        } catch (Exception e) {
            assertFalse(true);
        }
    }

    @Test
    public void deleteRate_givenValidRateId_thenStatus404_WithRateResponse() {
        when(surchargeRateRepository.findByRateId(100)).thenReturn(null);
        try {
            surchargeRateService2.deleteRate(100);
        } catch (Exception e) {
            assertEquals(e.getMessage(), RATE_ID_NOT_FOUND);
        }

    }
}

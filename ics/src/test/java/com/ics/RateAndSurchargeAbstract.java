package com.ics;

import com.ics.domain.Rate;
import com.ics.domain.RateAndSurcharge;
import com.ics.domain.Surcharge;
import com.ics.service.SurchargeRateService;
import com.ics.service.repository.SurchargeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

abstract public class RateAndSurchargeAbstract {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected SurchargeRateRepository surchargeRateRepository;
    @MockBean
    protected SurchargeRateService surchargeRateService;
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
    protected RateAndSurcharge RATE_SURCHARGE = RateAndSurcharge.builder()
                                                                .rate(RATE)
                                                                .surcharge(SURCHARGE)
                                                                .build();
}

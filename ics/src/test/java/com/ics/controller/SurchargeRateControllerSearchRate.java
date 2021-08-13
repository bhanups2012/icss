package com.ics.controller;

import com.ics.RateAndSurchargeAbstract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurchargeRateController.class)
public class SurchargeRateControllerSearchRate extends RateAndSurchargeAbstract {

    @Test
    public void givenValidRateId_thenStatus200_WithRateResponse() throws Exception {
        when(surchargeRateService.searchRate(100)).thenReturn(RATE_SURCHARGE);
        when(surchargeRateRepository.findByRateId(100)).thenReturn(RATE);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rms/searchRate")
                                                                          .contentType(MediaType.APPLICATION_JSON)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .param("rateId", String.valueOf(100));

        mockMvc.perform(mockRequest)
               .andExpect(status().isOk());
        verify(surchargeRateService, times(1)).searchRate(100);
    }

    @Test
    public void givenValidRateId_thenStatus400_WithRateResponse() throws Exception {
        given(surchargeRateService.searchRate(100)).willReturn(RATE_SURCHARGE);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rms/searchRate")
                                                                          .contentType(MediaType.APPLICATION_JSON)
                                                                          .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
               .andExpect(status().isBadRequest());
        verify(surchargeRateService, times(0)).searchRate(100);
    }
}

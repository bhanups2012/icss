package com.ics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ics.RateAndSurchargeAbstract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurchargeRateController.class)
public class SurchargeRateControllerUpdateRate extends RateAndSurchargeAbstract {

    @Autowired
    ObjectMapper mapper;

    @Test
    public void givenValidRate_thenStatus200_WithRateResponse() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/rms/updateRate")
                                                                          .contentType(MediaType.APPLICATION_JSON)
                                                                          .accept(MediaType.APPLICATION_JSON)
                                                                          .content(this.mapper.writeValueAsString(RATE));

        mockMvc.perform(mockRequest)
               .andExpect(status().isAccepted());
    }

    @Test
    public void givenValidRate_thenStatus400_WithRateResponse() throws Exception {
        given(surchargeRateService.searchRate(100)).willReturn(RATE_SURCHARGE);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/rms/updateRate")
                                                                          .contentType(MediaType.APPLICATION_JSON)
                                                                          .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
               .andExpect(status().isBadRequest());
    }
}

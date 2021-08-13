package com.ics.controller.validate;

import com.ics.domain.Rate;
import com.ics.service.exception.InvalidRateException;

import java.time.LocalDate;

public class RateValidator {
    public void validateRate(Rate rate) throws InvalidRateException {
        String errorMsg = "Rate required fields with Valid data: ";
        int errorMsgLength = errorMsg.length();
        if (rate.getRateId() == 0)
            errorMsg += "rateId:" + rate.getRateId();
        if (rate.getRateEffectiveDate() == null || rate.getRateEffectiveDate().isAfter(LocalDate.now()))
            errorMsg += ", rateEffectiveDate:" + rate.getRateEffectiveDate();
        if (rate.getRateExpirationDate() == null || rate.getRateExpirationDate().isBefore(LocalDate.now()))
            errorMsg += ", rateExpirationDate:" + rate.getRateExpirationDate();
        if (rate.getAmount() == 0)
            errorMsg += ", amount:" + rate.getAmount();
        if (errorMsgLength != errorMsg.length())
            throw new InvalidRateException(errorMsg);
    }
}

package com.ics.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Builder
public class RateAndSurcharge {
    private final Rate rate;
    private final Surcharge surcharge;
}

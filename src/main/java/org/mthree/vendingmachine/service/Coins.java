package org.mthree.vendingmachine.service;

import java.math.BigDecimal;

public enum Coins {
    PENNY( BigDecimal.valueOf(0.01)),
    NICKEL(BigDecimal.valueOf(0.05)),
    DIME(BigDecimal.valueOf(0.10)),
    QUARTER(BigDecimal.valueOf(0.25));

    private final BigDecimal value;

    Coins(BigDecimal value){
        this.value=value;
    }

    public BigDecimal getValue(){
        return this.value;
    }
}
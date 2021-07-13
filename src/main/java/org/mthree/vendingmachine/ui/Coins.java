package org.mthree.vendingmachine.ui;

import java.math.BigDecimal;

public enum Coins {
    PENNY(new BigDecimal("0.01")),
    NICKEL(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25"));

    private final BigDecimal value;

    Coins(BigDecimal value){
        this.value=value;
    }

    public BigDecimal getValue(){
        return this.value;
    }
}
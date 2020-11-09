package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BalanceTest {

    @Test
    public void shouldCreateBalance(){
        Balance balance = Balance.from("10,30", Currency.USD, "04.05.2020");

        assertTrue("10,30".equals(balance.getTotal().asString()));
        assertTrue("USD".equals(balance.getTotal().getCurrencyCode()));
        assertTrue("04.05.2020".equals(balance.getDate().asString()));
    }
}
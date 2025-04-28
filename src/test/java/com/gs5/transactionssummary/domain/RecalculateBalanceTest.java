package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.gs5.transactionssummary.domain.TransactionsExample.THOMAS_TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecalculateBalanceTest {

    private final static Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2020-10-29T10:15:30.00Z"), ZoneOffset.UTC);

    @Test
    public void shouldRecalculateTotalBalanceForNewTransactions() {
        Balance balance = Balance.from("12110", Currency.PLN, "01.05.2020");

        Balance actualBalance = balance.recalculate(THOMAS_TRANSACTIONS, Date.now(FIXED_CLOCK));

        assertTrue("19010.00".equals(actualBalance.getTotal().asString()));
        assertTrue("PLN".equals(actualBalance.getTotal().getCurrencyCode()));
    }
}
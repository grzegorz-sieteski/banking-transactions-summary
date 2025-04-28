package com.gs5.transactionssummary.domain;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionsExtractorTest {

    @Test
    public void shouldExtractTransactionsInGivenTimeRange() {
        val from = Date.from("06.05.2020");
        val to = Date.from("10.05.2020");

        List<BankingTransaction> transactionsInRange = TransactionsExtractor.extractTransactionsInGivenRange(TransactionsExample.NATALIE_TRANSACTIONS, from, to);

        assertTrue(transactionsInRange.size() == 2);
    }
}
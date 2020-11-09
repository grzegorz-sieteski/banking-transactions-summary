package com.gs5.transactionssummary.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.gs5.transactionssummary.domain.TransactionsExample.NATALIE_TRANSACTIONS;
import static com.gs5.transactionssummary.domain.TransactionsExample.THOMAS_TRANSACTIONS;
import static org.javamoney.moneta.Money.of;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TotalTurnoverCalculatorTest {


    @ParameterizedTest
    @MethodSource("testCases")
    public void shouldCalculateTotalTurnoverSumOFIncomeAndOutcomeTransactions(List<BankingTransaction> transactions, Money expectedTotalTurnover) {
        Money totalTurnover = TotalTurnoverCalculator.calculateTotalTurnover(transactions, Currency.PLN);

        assertTrue(expectedTotalTurnover.equals(totalTurnover));
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(THOMAS_TRANSACTIONS, Money.from(of(11500, "PLN"))),
                Arguments.of(NATALIE_TRANSACTIONS, Money.from(of(12750.50, "PLN"))),
                Arguments.of(new ArrayList<>(), Money.zero(Currency.PLN)));
    }
}
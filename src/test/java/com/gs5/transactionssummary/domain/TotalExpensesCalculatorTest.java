package com.gs5.transactionssummary.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.gs5.transactionssummary.domain.TransactionsExample.NATALIE_TRANSACTIONS;
import static com.gs5.transactionssummary.domain.TransactionsExample.THOMAS_TRANSACTIONS;
import static javax.money.Monetary.getCurrency;
import static org.javamoney.moneta.Money.of;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TotalExpensesCalculatorTest {


    @ParameterizedTest
    @MethodSource("testCases")
    public void shouldCalculateTotalExpensesSumOfOutcomeTransactionsInGivenCurrency(List<BankingTransaction> transactions, Money expectedTotalExpenses) {
        Money totalExpenses = TotalExpensesCalculator.calculateTotalExpenses(transactions, Currency.PLN);

        assertTrue(expectedTotalExpenses.equals(totalExpenses));
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(THOMAS_TRANSACTIONS, Money.from(of(2300.00, getCurrency("PLN")))),
                Arguments.of(NATALIE_TRANSACTIONS, Money.from(of(2250.50, getCurrency("PLN")))),
                Arguments.of(new ArrayList<>(), Money.zero(Currency.PLN)));
    }
}
package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @ParameterizedTest
    @MethodSource("testCases")
    public void shouldReturnMoneyInExceptedFormat(String value ,Currency currencyCode, String moneyExcepted, String currencyCodeExcepted) {
        Money money = Money.of(value, currencyCode);

        assertEquals(moneyExcepted, money.asString());
        assertEquals(currencyCodeExcepted, money.getCurrencyCode());
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("100.55", Currency.PLN, "100.55", "PLN"),
                Arguments.of("-100.50", Currency.PLN, "-100.50", "PLN"));
    }

    @Test
    public void shouldNoCreateWhenInputsAreNull() {
        assertThrows(NullPointerException.class, () -> Money.of(null, null));
    }
}
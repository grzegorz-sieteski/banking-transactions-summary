package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import static org.javamoney.moneta.Money.of;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyExchangeTest {


    @Test
    public void shouldExchangeMoneyFromGivenUSDToPLN() {
        Money oneDollar = Money.from(of(1, "USD"));
        CurrencyExchange currencyExchange = (money, currencyCode) -> Money.of(money.asString(), currencyCode);

        Money exchangedMoney = currencyExchange.exchange(oneDollar, Currency.PLN);

        assertTrue(exchangedMoney.equals(Money.from(org.javamoney.moneta.Money.of(1, "PLN"))));
    }
}
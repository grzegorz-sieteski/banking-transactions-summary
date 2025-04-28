package com.gs5.transactionssummary.domain;

public interface CurrencyExchange {

    Money exchange(Money money, Currency targetCurrency);

}

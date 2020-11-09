package com.gs5.transactionssummary.domain;

import lombok.Getter;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public final class Balance {
    private final Date date;
    private final Money total;

    private Balance(Money total, Date date) {
        this.date = requireNonNull(date, "Balance date can't be null!");
        this.total = requireNonNull(total, "Balance total can't be null!");
    }

    public Balance recalculate(List<BankingTransaction> transactions, Date recalculationDate) {
        requireNonNull(transactions, "On recalculation balance transactions can't be null!");
        Money actualTotalBalanceValue = BalanceCalculator.recalculate(total, transactions);
        return new Balance(actualTotalBalanceValue, recalculationDate);
    }

    public static Balance from(String total, Currency currency, String date) {
        Date createdDate = Date.from(date);
        Money money = Money.of(total, currency);
        return new Balance(money, createdDate);
    }
}
package com.gs5.transactionssummary.domain;

import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.List;

class TotalExpensesCalculator {

    static final Money calculateTotalExpenses(List<BankingTransaction> transactions, Currency accountCurrency) {
        return transactions.stream()
                .filter(BankingTransaction::isOutcome)
                .map(BankingTransaction::getValue)
                .map(Money::asMonetaryAmount)
                .reduce(MonetaryFunctions.sum())
                .map(Money::from)
                .orElse(Money.zero(accountCurrency));
    }
}
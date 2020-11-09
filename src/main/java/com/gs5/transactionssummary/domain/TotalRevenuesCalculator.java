package com.gs5.transactionssummary.domain;

import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.List;

class TotalRevenuesCalculator {

    static Money calculateTotalRevenues(List<BankingTransaction> transactions, Currency currencyAccount) {
        return transactions.stream()
                .filter(BankingTransaction::isIncome)
                .map(BankingTransaction::getValue)
                .map(Money::asMonetaryAmount)
                .reduce(MonetaryFunctions.sum())
                .map(Money::from)
                .orElse(Money.zero(currencyAccount));
    }
}
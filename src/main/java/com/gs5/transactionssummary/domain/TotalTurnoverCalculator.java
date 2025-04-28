package com.gs5.transactionssummary.domain;

import org.javamoney.moneta.function.MonetaryFunctions;

import java.util.List;

class TotalTurnoverCalculator {

    static Money calculateTotalTurnover(List<BankingTransaction> transactions, Currency accountCurrency) {
        return transactions.stream()
                .map(BankingTransaction::getValue)
                .map(com.gs5.transactionssummary.domain.Money::asMonetaryAmount)
                .reduce(MonetaryFunctions.sum())
                .map(Money::from)
                .orElse(Money.zero(accountCurrency));
    }
}
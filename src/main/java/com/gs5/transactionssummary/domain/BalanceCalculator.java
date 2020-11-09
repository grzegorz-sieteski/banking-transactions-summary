package com.gs5.transactionssummary.domain;


import lombok.val;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.stream.Stream;

class BalanceCalculator {


    static final Money recalculate(Money actualBalance, List<BankingTransaction> transactions) {
        val balance = actualBalance.asMonetaryAmount();
        return Stream.concat(createTransactionValuesStream(transactions), Stream.of(balance))
                .reduce(MonetaryFunctions.sum())
                .map(Money::from)
                .orElse(Money.zero(Currency.valueOf(balance.getCurrency().getCurrencyCode())));
    }

    private static Stream<MonetaryAmount> createTransactionValuesStream(List<BankingTransaction> transactions) {
        return transactions
                .stream()
                .map(BankingTransaction::getValueIfOutcomeNegateIt)
                .map(Money::asMonetaryAmount);
    }
}
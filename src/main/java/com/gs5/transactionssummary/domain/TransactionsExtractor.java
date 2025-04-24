package com.gs5.transactionssummary.domain;

import java.util.List;
import java.util.function.Predicate;

class TransactionsExtractor {

    static List<BankingTransaction> extractTransactionsInGivenRange(List<BankingTransaction> transactions, Date from, Date to) {
        return transactions
                .stream()
                .filter(isInRange(from, to))
                .toList();
    }

    private static Predicate<BankingTransaction> isInRange(Date from, Date to) {
        return t -> (t.getDate().isAfter(from) || t.getDate().isEqual(from)) && (t.getDate().isBefore(to) || t.getDate().isEqual(to));
    }
}
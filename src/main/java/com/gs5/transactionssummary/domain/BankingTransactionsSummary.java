package com.gs5.transactionssummary.domain;

import lombok.Builder;

import static java.util.Objects.requireNonNull;


public final class BankingTransactionsSummary {

    private final Client client;
    private final Balance balance;

    private final Money totalRevenues;
    private final Money totalExpenses;
    private final Money totalTurnover;

    @Builder
    private BankingTransactionsSummary(Client client, Balance balance, Money totalRevenues, Money totalExpenses, Money totalTurnover) {
        this.client = requireNonNull(client, "client in bankingTransactionsSummary can't be null!");
        this.balance = requireNonNull(balance, "balance in bankingTransactionsSummary can't be null!");
        this.totalRevenues = requireNonNull(totalRevenues, "totalRevenues in bankingTransactionsSummary can't be null!");
        this.totalExpenses = requireNonNull(totalExpenses, "totalExpenses in bankingTransactionsSummary can't be null!");
        this.totalTurnover = requireNonNull(totalTurnover, "totalTurnover in bankingTransactionsSummary can't be null!");
    }


    public Client getClient() {
        return client;
    }

    public Balance getBalance() {
        return balance;
    }

    public String getTotalExpensesAsString() {
        return totalExpenses.asString();
    }

    public String getTotalRevenuesAsString() {
        return totalRevenues.asString();
    }

    public String getTotalTurnoverAsString() {
        return totalTurnover.asString();
    }
}
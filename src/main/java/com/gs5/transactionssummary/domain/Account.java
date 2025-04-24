package com.gs5.transactionssummary.domain;

import lombok.Builder;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import static com.gs5.transactionssummary.domain.TransactionsExtractor.extractTransactionsInGivenRange;
import static java.util.Objects.requireNonNull;


public final class Account {

    private final Clock clock;
    private final Client client;
    private final Balance balance;
    private final List<BankingTransaction> transactions;

    @Builder
    private Account(Client client, Balance balance, List<BankingTransaction> transactions, Clock clock) {
        this.client = requireNonNull(client, "Account client can't be null!");
        this.balance = requireNonNull(balance, "Account balance can't be null!");
        this.transactions = requireNonNull(transactions, "Account transactions can't be null!");
        this.clock = requireNonNull(clock, "Account clock can't be null!");
    }


    public BankingTransactionsSummary generateBankingTransactionsSummary() {
        return BankingTransactionsSummary
                .builder()
                .client(client)
                .balance(recalculateBalance())
                .totalRevenues(calculateTotalRevenues())
                .totalExpenses(calculateTotalExpenses())
                .totalTurnover(calculateTotalTurnover())
                .build();
    }

    private Balance recalculateBalance() {
        return balance.recalculate(transactions, Date.now(clock));
    }

    private List<BankingTransaction> getTransactionFromNowToLastBalanceCreation() {
        final var lastBalanceDate = balance.getDate();
        final var now = Date.now(clock);
        return extractTransactionsInGivenRange(transactions, lastBalanceDate, now);
    }

    private Money calculateTotalRevenues() {
        return TotalRevenuesCalculator.calculateTotalRevenues(transactions, accountCurrency());
    }

    private Currency accountCurrency() {
        Money total = balance.getTotal();
        return total.getCurrency();
    }

    private Money calculateTotalExpenses() {
        return TotalExpensesCalculator.calculateTotalExpenses(transactions, accountCurrency());
    }

    private Money calculateTotalTurnover() {
        return TotalTurnoverCalculator.calculateTotalTurnover(transactions, accountCurrency());
    }

    Account exchangeTransactionWithOtherCurrencyToAccountCurrency(CurrencyExchange currencyExchange) {
        final var transactionsExchanged = new ArrayList<BankingTransaction>();
        final var accountTotalBalance = balance.getTotal();
        transactions.forEach(transaction -> {
            if (accountTotalBalance.getCurrencyUnit().equals(transaction.getValue().getCurrencyUnit())) {
                transactionsExchanged.add(transaction);
            } else {
                final var exchangedMoney = currencyExchange.exchange(transaction.getValue(), accountTotalBalance.getCurrency());
                final var bankingTransactionWithConvertedValue = transaction.changeValue(exchangedMoney);
                transactionsExchanged.add(bankingTransactionWithConvertedValue);
            }
        });

        return Account
                .builder()
                .clock(clock)
                .transactions(transactionsExchanged)
                .balance(balance)
                .client(client)
                .build();
    }

    Account filterTransactionFromNowToBalanceCreationDate() {
        return Account
                .builder()
                .clock(clock)
                .transactions(getTransactionFromNowToLastBalanceCreation())
                .balance(balance)
                .client(client)
                .build();
    }
}

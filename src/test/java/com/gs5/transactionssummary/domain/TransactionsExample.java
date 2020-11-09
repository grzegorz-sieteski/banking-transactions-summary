package com.gs5.transactionssummary.domain;

import java.util.ArrayList;
import java.util.List;

import static com.gs5.transactionssummary.domain.BankingTransaction.Type.INCOME;
import static com.gs5.transactionssummary.domain.BankingTransaction.Type.OUTCOME;

public class TransactionsExample {

    static final List<BankingTransaction> THOMAS_TRANSACTIONS = new ArrayList<>() {{
        add(new BankingTransaction(INCOME, "salary", Date.from("04.05.2020"), Money.of("7500", Currency.PLN)));
        add(new BankingTransaction(OUTCOME, "mortgage", Date.from("06.05.2020"), Money.of("1100", Currency.PLN)));
        add(new BankingTransaction(INCOME, "interests", Date.from("10.05.2020"), Money.of("1700", Currency.PLN)));
        add(new BankingTransaction(OUTCOME, "transfer", Date.from("11.05.2020"), Money.of("1200", Currency.PLN)));
    }};

    static final List<BankingTransaction> NATALIE_TRANSACTIONS = new ArrayList<>() {{
        add(new BankingTransaction(INCOME, "salary", Date.from("04.05.2020"), Money.of("10500", Currency.PLN)));
        add(new BankingTransaction(OUTCOME, "transfer", Date.from("10.05.2020"), Money.of("1200", Currency.PLN)));
        add(new BankingTransaction(OUTCOME, "transfer", Date.from("10.05.2020"), Money.of("1050,50", Currency.PLN)));
    }};

    static final List<BankingTransaction> JHON_TRANSACTIONS_WITH_DIFFERENT_CURRENCIES = new ArrayList<>() {{
        add(new BankingTransaction(INCOME, "salary", Date.from("04.05.2020"), Money.of("10500", Currency.PLN)));
        add(new BankingTransaction(OUTCOME, "transfer", Date.from("10.05.2020"), Money.of("1200", Currency.USD)));
        add(new BankingTransaction(OUTCOME, "transfer", Date.from("10.05.2020"), Money.of("1050,50", Currency.PLN)));
    }};
}

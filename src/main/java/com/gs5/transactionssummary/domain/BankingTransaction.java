package com.gs5.transactionssummary.domain;

import gs5.bankingtransactions.summary.model.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.val;

import static java.util.Objects.requireNonNull;

@Getter
public final class BankingTransaction {


    enum Type {
        INCOME,
        OUTCOME;
    }

    private final Type type;
    private final String description;
    private final Date date;
    private final Money value;


    @Builder
    BankingTransaction(Type type, String description, Date date, Money value) {
        this.type = requireNonNull(type, "Transaction type can't be null!");
        this.description = requireNonNull(description, "Transaction description can't be null!");
        this.date = requireNonNull(date, "Transaction date can't be null!");
        this.value = requireNonNull(value, "Transaction value can't be null!");
    }

    Money getValueIfOutcomeNegateIt() {
        return isOutcome() ? value.negate() : value;
    }

    boolean isOutcome() {
        return Type.OUTCOME.equals(type);
    }

    boolean isIncome() {
        return Type.INCOME.equals(type);
    }

    public String getTypeAsString() {
        return type.name().toLowerCase();
    }

    BankingTransaction changeValue(Money value) {
        return BankingTransaction
                .builder()
                .type(type)
                .description(description)
                .value(value)
                .date(date)
                .build();
    }

    public static BankingTransaction from(String type, String description, String date, String value, Currency currency) {
        final var transactionType = Type.valueOf(type.toUpperCase());
        final var money = Money.of(value, currency);
        final var creationDate = Date.from(date);
        return BankingTransaction
                .builder()
                .type(transactionType)
                .description(description)
                .value(money)
                .date(creationDate)
                .build();
    }
}
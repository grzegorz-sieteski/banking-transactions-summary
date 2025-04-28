package com.gs5.transactionssummary.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.javamoney.moneta.format.AmountFormatParams;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Money {

    private final static MonetaryAmountFormat MONETARY_AMOUNT_FORMAT = MonetaryFormats.getAmountFormat(
            AmountFormatQueryBuilder.of(Locale.getDefault())
                    .set(AmountFormatParams.PATTERN, "###0.00;-###0.00")
                    .build());


    private final MonetaryAmount value;


    public String getCurrencyCode() {
        return value.getCurrency().getCurrencyCode();
    }

    public Currency getCurrency() {
        return Currency.valueOf(value.getCurrency().getCurrencyCode());
    }

    public String asString() {
        return MONETARY_AMOUNT_FORMAT.format(value);
    }

    CurrencyUnit getCurrencyUnit() {
        return value.getCurrency();
    }

    MonetaryAmount asMonetaryAmount() {
        return value;
    }

    Money negate() {
        return new Money(value.negate());
    }

    public static Money of(String value, Currency currency) {
        requireNonNull(value, "total in balance can't be null!");
        requireNonNull(currency, "currencyCode in balance can't be null!");
        validateFormat(value);

        final var totalInCorrectFormat = value.replace(",", ".");
        final var money = org.javamoney.moneta.Money.of(new BigDecimal(totalInCorrectFormat), currency.name());
        return new Money(money);
    }

    private static void validateFormat(String value) {
        if (!value.matches("^([-+] ?)?[0-9]+(.[0-9]{2})?$"))
            throw new IllegalArgumentException("Money value must be in format *d.dd (example 10.20) but it is: " + value);
    }

    public static Money from(MonetaryAmount monetaryAmount) {
        requireNonNull(monetaryAmount, "monetaryAmount can't be null!");
        return new Money(monetaryAmount);
    }

    public static Money zero(Currency currencyCode) {
        requireNonNull(currencyCode, "currency can't be null!");
        final var currencyUnit = Monetary.getCurrency(currencyCode.name());
        final var money = org.javamoney.moneta.Money.zero(currencyUnit);
        return new Money(money);
    }
}
package com.gs5.transactionssummary.domain;

import lombok.EqualsAndHashCode;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
public final class Date {
    private static final DateTimeFormatter BALANCE_DATE_STRING_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final LocalDate date;

    private Date(LocalDate date) {
        this.date = date;
    }


    public LocalDate asLocalDate() {
        return date;
    }

    public String asString() {
        return date.format(BALANCE_DATE_STRING_FORMATTER);
    }

    boolean isEqual(Date date) {
        return this.date.isEqual(date.asLocalDate());
    }

    boolean isBefore(Date date) {
        return this.date.isBefore(date.asLocalDate());
    }

    boolean isAfter(Date date) {
        return this.date.isAfter(date.asLocalDate());
    }

    public static Date from(String date) {
        validateNotNull(date);
        validateDateFormat(date);
        final var createdDate = LocalDate.parse(date, BALANCE_DATE_STRING_FORMATTER);
        return new Date(createdDate);
    }

    private static void validateNotNull(String date) {
        requireNonNull(date, "Date can't be null!");
    }

    private static void validateDateFormat(String date) {
        if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}"))
            throw new IllegalArgumentException("Date must be in format dd.MM.yyyy: " + date);
    }

    public static Date now(Clock clock) {
        final var now = LocalDate.now(clock);
        return new Date(now);
    }
}
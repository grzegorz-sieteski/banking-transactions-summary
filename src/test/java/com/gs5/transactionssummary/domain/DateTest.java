package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTest {

    @Test
    public void shouldCreateDateFromStringWithAcceptedFormat() {
        String dateAsString = "06.05.2020";

        Date date = Date.from(dateAsString);

        assertEquals(dateAsString, date.asString());
        assertEquals(LocalDate.of(2020, 05, 06), date.asLocalDate());
    }

    @Test
    public void shouldBeEqualsWhenHaveSameValue() {
        assertEquals(Date.from("06.05.2020"), Date.from("06.05.2020"));
    }

    @Test
    public void shouldNoCreateWhenInputAreNotInAcceptedFormat() {
        assertThrows(IllegalArgumentException.class, () -> Date.from("06-05-2020"));
    }

    @Test
    public void shouldNoCreateWhenInputAreNull() {
        assertThrows(NullPointerException.class, () -> Date.from(null));
    }
}
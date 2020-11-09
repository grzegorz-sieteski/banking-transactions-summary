package com.gs5.transactionssummary.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class AccountExample {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2020-10-29T10:00:00.00Z"), ZoneOffset.UTC);


    static final Account TOMAS_KOWALSKI_ACCOUNT = Account
            .builder()
            .client(Client.builder().name("Tom").surname("Kowalski").build())
            .balance(Balance.from("30", Currency.PLN, "06.05.2020"))
            .transactions(TransactionsExample.THOMAS_TRANSACTIONS)
            .clock(FIXED_CLOCK)
            .build();


    static final Account JOHN_KOWALSKI_ACCOUNT = Account
            .builder()
            .client(Client.builder().name("jOHN").surname("Kowalski").build())
            .balance(Balance.from("130", Currency.PLN, "01.05.2020"))
            .transactions(TransactionsExample.JHON_TRANSACTIONS_WITH_DIFFERENT_CURRENCIES)
            .clock(FIXED_CLOCK)
            .build();
}

package com.gs5.transactionssummary.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Getter
public final class Client {
    private final String name;
    private final String surname;
    private final Optional<String> country;


    @Builder
    public Client(String name, String surname, String country) {
        this.name = requireNonNull(name, "Client name can't be null!");
        this.surname = requireNonNull(surname, "Client surname can't be null!");
        this.country = Optional.ofNullable(country);
    }
}
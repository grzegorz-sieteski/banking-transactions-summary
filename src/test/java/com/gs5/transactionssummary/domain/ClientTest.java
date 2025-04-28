package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    public void shouldCreateClient() {
        Client client = Client.builder().name("Jon").surname("Sho").country("Poland").build();

        assertEquals("Jon", client.getName());
        assertEquals("Sho", client.getSurname());
        assertEquals("Poland", client.getCountry().get());
    }

    @Test
    public void shouldCreateClientWithoutCountry() {
        Client client = Client.builder().name("Jon").surname("Sho").build();

        Optional<String> clientCountry = client.getCountry();
        assertTrue(clientCountry.isEmpty());
    }

    @Test
    public void shouldNotCreateCreateClientWithoutSurname() {
        assertThrows(NullPointerException.class, () -> Client.builder().name("Jon").build());
    }

    @Test
    public void shouldNotCreateCreateClientWithoutName() {
        assertThrows(NullPointerException.class, () -> Client.builder().surname("Sho").build());
    }
}
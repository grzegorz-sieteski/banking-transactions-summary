package com.gs5.transactionssummary.domain;

import org.junit.jupiter.api.Test;

import static com.gs5.transactionssummary.domain.AccountExample.JOHN_KOWALSKI_ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private final CurrencyExchange currencyExchange = (money, currency) -> Money.of(money.asString(), currency);


    @Test
    public void shouldGenerateBankingTransactionsSummary() {
        BankingTransactionsSummary bankingTransactionsSummary = JOHN_KOWALSKI_ACCOUNT
                .filterTransactionFromNowToBalanceCreationDate()
                .exchangeTransactionWithOtherCurrencyToAccountCurrency(currencyExchange)
                .generateBankingTransactionsSummary();

        assertEquals("2250,50", bankingTransactionsSummary.getTotalExpensesAsString());
        assertEquals("8379,50", bankingTransactionsSummary.getBalance().getTotal().asString());
        assertEquals("10500,00", bankingTransactionsSummary.getTotalRevenuesAsString());
        assertEquals("12750,50", bankingTransactionsSummary.getTotalTurnoverAsString());
    }
}
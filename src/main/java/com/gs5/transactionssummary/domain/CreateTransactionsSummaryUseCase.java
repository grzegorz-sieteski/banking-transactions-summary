package com.gs5.transactionssummary.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTransactionsSummaryUseCase {
    private final CurrencyExchange currencyExchange;

    public final BankingTransactionsSummary forAccount(Account account) {
        return account
                .filterTransactionFromNowToBalanceCreationDate()
                .exchangeTransactionWithOtherCurrencyToAccountCurrency(currencyExchange)
                .generateBankingTransactionsSummary();
    }
}
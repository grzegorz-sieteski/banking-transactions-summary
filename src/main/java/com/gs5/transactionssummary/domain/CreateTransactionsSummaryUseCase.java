package com.gs5.transactionssummary.domain;


public class CreateTransactionsSummaryUseCase {
    private final CurrencyExchange currencyExchange;

    public CreateTransactionsSummaryUseCase(CurrencyExchange currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public final BankingTransactionsSummary forAccount(Account account) {
        return account
                .filterTransactionFromNowToBalanceCreationDate()
                .exchangeTransactionWithOtherCurrencyToAccountCurrency(currencyExchange)
                .generateBankingTransactionsSummary();
    }
}
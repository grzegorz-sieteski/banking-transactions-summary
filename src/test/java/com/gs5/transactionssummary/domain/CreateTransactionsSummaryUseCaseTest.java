package com.gs5.transactionssummary.domain;


import org.junit.jupiter.api.Test;

import static com.gs5.transactionssummary.domain.AccountExample.TOMAS_KOWALSKI_ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTransactionsSummaryUseCaseTest {

    private final CurrencyExchange currencyExchange = (money, currencyCode) -> Money.of(money.asString(), currencyCode);

    @Test
    public void shouldCreateTransactionSummaryForGivenClient(){
        CreateTransactionsSummaryUseCase createTransactionsSummaryUseCase = new CreateTransactionsSummaryUseCase(currencyExchange);

        BankingTransactionsSummary bankingTransactionsSummary = createTransactionsSummaryUseCase.forAccount(TOMAS_KOWALSKI_ACCOUNT);

        assertEquals("2300.00",bankingTransactionsSummary.getTotalExpensesAsString());
        assertEquals("-570.00",bankingTransactionsSummary.getBalance().getTotal().asString());
        assertEquals("1700.00",bankingTransactionsSummary.getTotalRevenuesAsString());
        assertEquals("4000.00",bankingTransactionsSummary.getTotalTurnoverAsString());
    }
}
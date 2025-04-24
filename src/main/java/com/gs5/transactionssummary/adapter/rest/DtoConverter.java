package com.gs5.transactionssummary.adapter.rest;

import com.gs5.transactionssummary.domain.Balance;
import com.gs5.transactionssummary.domain.BankingTransactionsSummary;
import com.gs5.transactionssummary.domain.Client;
import com.gs5.transactionssummary.domain.Money;
import gs5.bankingtransactions.summary.model.Info;
import gs5.bankingtransactions.summary.model.Summary;
import gs5.bankingtransactions.summary.model.TransactionsSummary;
import lombok.val;

public class DtoConverter {


    public TransactionsSummary convertToTransactionsSummary(BankingTransactionsSummary bts) {
        final var infoDTO = convertToInfoDTO(bts.getClient());
        final var balanceDTO = covertToBalanceDTO(bts.getBalance());
        final var summaryDTO = convertToSummaryDTO(bts);
        return new TransactionsSummary()
                .info(infoDTO)
                .balance(balanceDTO)
                .summary(summaryDTO);
    }

    private Info convertToInfoDTO(Client client) {
        return new Info()
                .name(client.getName())
                .surname(client.getSurname())
                .country(client.getCountry().orElse(null));
    }

    private gs5.bankingtransactions.summary.model.Balance covertToBalanceDTO(Balance balance) {
        Money balanceDomainTotal = balance.getTotal();
        return new gs5.bankingtransactions.summary.model.Balance()
                .currency(gs5.bankingtransactions.summary.model.Balance.CurrencyEnum.fromValue(balanceDomainTotal.getCurrencyCode()))
                .total(balanceDomainTotal.asString())
                .date(balance.getDate().asString());
    }

    private Summary convertToSummaryDTO(BankingTransactionsSummary bts) {
        return new Summary()
                .currency(Summary.CurrencyEnum.fromValue(bts.getBalance().getTotal().getCurrencyCode()))
                .totalExpenses(bts.getTotalExpensesAsString())
                .totalRevenues(bts.getTotalRevenuesAsString())
                .totalTurnover(bts.getTotalTurnoverAsString());
    }
}
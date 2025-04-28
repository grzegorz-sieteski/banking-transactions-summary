package com.gs5.transactionssummary.adapter.rest.converter;

import com.gs5.transactionssummary.domain.BankingTransactionsSummary;
import gs5.bankingtransactions.summary.model.Summary;
import gs5.bankingtransactions.summary.model.TransactionsSummary;

import static com.gs5.transactionssummary.adapter.rest.converter.InfoDtoConverter.convertToInfoDTO;

public class TransactionSummaryDtoConverter {


    public TransactionsSummary convertToTransactionsSummary(BankingTransactionsSummary bts) {
        final var infoDTO = convertToInfoDTO(bts.getClient());
        final var balanceDTO = BalanceDtoConverter.covertToBalanceDTO(bts.getBalance());
        final var summaryDTO = convertToSummaryDTO(bts);
        return new TransactionsSummary()
                .info(infoDTO)
                .balance(balanceDTO)
                .summary(summaryDTO);
    }

    private Summary convertToSummaryDTO(BankingTransactionsSummary bts) {
        return new Summary()
                .currency(Summary.CurrencyEnum.fromValue(bts.getBalance().getTotal().getCurrencyCode()))
                .totalExpenses(bts.getTotalExpensesAsString())
                .totalRevenues(bts.getTotalRevenuesAsString())
                .totalTurnover(bts.getTotalTurnoverAsString());
    }
}
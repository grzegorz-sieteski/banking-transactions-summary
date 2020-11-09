package com.gs5.transactionssummary.adapter.rest;


import com.gs5.transactionssummary.domain.CreateTransactionsSummaryUseCase;
import gs5.bankingtransactions.summary.api.BankingtransactionsApi;
import gs5.bankingtransactions.summary.model.TransactionsSummary;
import gs5.bankingtransactions.summary.model.TransactionsSummaryRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
public class BankingTransactionsSummaryResource implements BankingtransactionsApi {
    private final CreateTransactionsSummaryUseCase createTransactionsSummary;
    private final DomainConverter domainConverter;
    private final DtoConverter dtoConverter;


    @Override
    public ResponseEntity<List<TransactionsSummary>> createTransactionsSummary(TransactionsSummaryRequest transactionsSummaryRequest) {
        return transactionsSummaryRequest
                .getClients()
                .getClient()
                .stream()
                .map(domainConverter::convertToDomain)
                .map(createTransactionsSummary::forAccount)
                .map(dtoConverter::convertToTransactionsSummary)
                .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }
}
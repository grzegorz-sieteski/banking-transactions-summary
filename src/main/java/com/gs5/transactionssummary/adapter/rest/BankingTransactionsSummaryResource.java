package com.gs5.transactionssummary.adapter.rest;


import com.gs5.transactionssummary.adapter.rest.converter.DomainConverter;
import com.gs5.transactionssummary.adapter.rest.converter.TransactionSummaryDtoConverter;
import com.gs5.transactionssummary.domain.CreateTransactionsSummaryUseCase;
import com.gs5.transactionssummary.domain.GetAllAccountUseCase;
import com.gs5.transactionssummary.domain.UpgradeBalanceForAccountUseCase;
import gs5.bankingtransactions.summary.api.*;
import gs5.bankingtransactions.summary.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

import static com.gs5.transactionssummary.adapter.rest.converter.AccountsDtoConverter.convertToApiModel;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
public class BankingTransactionsSummaryResource implements BankingtransactionsApi {
    private final CreateTransactionsSummaryUseCase createTransactionsSummary;
    private final DomainConverter domainConverter;
    private final TransactionSummaryDtoConverter transactionSummaryDtoConverter;
    private final GetAllAccountUseCase getAllAccountUseCase;
    private final UpgradeBalanceForAccountUseCase upgradeBalanceForAccountUseCase;


    @Override
    public ResponseEntity<List<TransactionsSummary>> createTransactionsSummary(TransactionsSummaryRequest transactionsSummaryRequest) {
        return transactionsSummaryRequest
                .getClients()
                .getClient()
                .stream()
                .map(domainConverter::convertToDomain)
                .map(createTransactionsSummary::forAccount)
                .map(transactionSummaryDtoConverter::convertToTransactionsSummary)
                .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }


    @Override
    public ResponseEntity<Accounts> getAccounts() {
        final var accounts = new Accounts().accounts(convertToApiModel(getAllAccountUseCase.get()));
        return ResponseEntity.ok(accounts);
    }

    @Override
    public ResponseEntity<AccountsResponse> createOrUpdateAccounts(AccountsRequest accountsRequest) {
        accountsRequest
                .getAccounts()
                .stream()
                .map(Account::getClient)
                .filter(Objects::nonNull)
                .map(domainConverter::convertToDomain)
                .toList()
                .forEach(upgradeBalanceForAccountUseCase::upgradeForGivenAccount);

        return ResponseEntity.ok(new AccountsResponse().message("Balance upgraded"));
    }
}
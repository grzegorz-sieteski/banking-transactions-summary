package com.gs5.transactionssummary.adapter.rest.converter;

import com.gs5.transactionssummary.domain.Accounts;
import com.gs5.transactionssummary.domain.BankingTransaction;
import gs5.bankingtransactions.summary.model.Account;
import gs5.bankingtransactions.summary.model.Client;
import gs5.bankingtransactions.summary.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class AccountsDtoConverter {

    public static List<Account> convertToApiModel(Accounts accountsAggregate) {
        return accountsAggregate
                .getAccounts()
                .stream()
                .map(AccountsDtoConverter::convertAccount)
                .collect(Collectors.toList());
    }

    private static Account convertAccount(com.gs5.transactionssummary.domain.Account account) {
        return new Account()
                .client(new Client()
                        .info(InfoDtoConverter.convertToInfoDTO(account.client()))
                        .balance(BalanceDtoConverter.convertToBalanceDTO(account.balance()))
                        .transactions(convertTransactions(account.transactions())));
    }

    private static List<Transaction> convertTransactions(List<BankingTransaction> transactions) {
        return transactions.stream()
                .map(AccountsDtoConverter::convertTransaction)
                .collect(Collectors.toList());
    }

    private static Transaction convertTransaction(BankingTransaction transaction) {
        return new Transaction(
                Transaction.TypeEnum.fromValue(transaction.getTypeAsString()),
                transaction.getDescription(),
                transaction.getDate().asString(),
                transaction.getValue().asString(),
                Transaction.CurrencyEnum.fromValue(transaction.getValue().getCurrency().name())
        );
    }
}


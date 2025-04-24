package com.gs5.transactionssummary.adapter.rest;

import com.gs5.transactionssummary.domain.Account;
import com.gs5.transactionssummary.domain.Balance;
import com.gs5.transactionssummary.domain.BankingTransaction;
import com.gs5.transactionssummary.domain.Client;
import gs5.bankingtransactions.summary.model.Info;
import gs5.bankingtransactions.summary.model.Transaction;

import java.time.Clock;
import java.util.List;

import static com.gs5.transactionssummary.domain.Currency.valueOf;


public class DomainConverter {

    private final Clock clock;

    public DomainConverter(Clock clock) {
        this.clock = clock;
    }


    Account convertToDomain(gs5.bankingtransactions.summary.model.Client client) {
        final var clientDomain = convertToClient(client.getInfo());
        final var balanceDomain = convertToBalance(client.getBalance());
        final var bankingTransactionsDomain = convertToBankingTransactions(client.getTransactions());
        return Account.builder()
                .clock(clock)
                .client(clientDomain)
                .balance(balanceDomain)
                .transactions(bankingTransactionsDomain)
                .build();
    }

    private Client convertToClient(Info info) {
        return Client.builder()
                .name(info.getName())
                .surname(info.getSurname())
                .country(info.getCountry())
                .build();
    }

    private Balance convertToBalance(gs5.bankingtransactions.summary.model.Balance balance) {
        return Balance.from(balance.getTotal(), valueOf(balance.getCurrency().getValue()), balance.getDate());
    }

    private List<BankingTransaction> convertToBankingTransactions(List<Transaction> transactions) {
        return transactions
                .stream()
                .map(this::convertToTransactionDomain)
                .toList();
    }

    private BankingTransaction convertToTransactionDomain(Transaction transaction) {
        return BankingTransaction.from(transaction.getType().getValue(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getValue(),
                valueOf(transaction.getCurrency().getValue()));
    }
}
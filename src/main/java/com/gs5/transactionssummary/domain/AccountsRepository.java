package com.gs5.transactionssummary.domain;

public interface AccountsRepository {
    Accounts loadAll();
    Account upgradeBalanceFor(Account account);
}

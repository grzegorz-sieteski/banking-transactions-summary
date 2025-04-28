package com.gs5.transactionssummary.domain;

import java.util.List;
import java.util.Objects;

public final class Accounts {

    private final List<Account> accounts;

    public Accounts(List<Account> accounts) {
        this.accounts = Objects.requireNonNull(accounts);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}

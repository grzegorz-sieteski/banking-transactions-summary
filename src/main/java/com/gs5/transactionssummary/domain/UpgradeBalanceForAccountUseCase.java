package com.gs5.transactionssummary.domain;

public class UpgradeBalanceForAccountUseCase {

    private final AccountsRepository accountsRepository;

    public UpgradeBalanceForAccountUseCase(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Account upgradeForGivenAccount(Account account) {
        return accountsRepository.upgradeBalanceFor(account);
    }
}

package com.gs5.transactionssummary.domain;

public final class GetAllAccountUseCase {

    private final AccountsRepository accountsRepository;

    public GetAllAccountUseCase(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Accounts get() {
        return accountsRepository.loadAll();
    }
}

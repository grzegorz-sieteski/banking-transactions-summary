package com.gs5.transactionssummary.adapter.rest.converter;

import com.gs5.transactionssummary.domain.Balance;
import com.gs5.transactionssummary.domain.Money;

public class BalanceDtoConverter {
    public static gs5.bankingtransactions.summary.model.Balance covertToBalanceDTO(Balance balance) {
        Money balanceDomainTotal = balance.getTotal();
        return new gs5.bankingtransactions.summary.model.Balance()
                .currency(gs5.bankingtransactions.summary.model.Balance.CurrencyEnum.fromValue(balanceDomainTotal.getCurrencyCode()))
                .total(balanceDomainTotal.asString())
                .date(balance.getDate().asString());
    }
}

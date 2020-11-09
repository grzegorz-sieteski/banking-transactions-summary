package com.gs5.transactionssummary.adapter.rest;

import com.gs5.transactionssummary.domain.CreateTransactionsSummaryUseCase;
import com.gs5.transactionssummary.domain.CurrencyExchange;
import com.gs5.transactionssummary.domain.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class BankingTransactionsSummaryConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DomainConverter domainConverter(Clock clock) {
        return new DomainConverter(clock);
    }

    @Bean
    public DtoConverter dtoConverter(){
        return new DtoConverter();
    }

    @Bean
    public CreateTransactionsSummaryUseCase createTransactionsSummaryUseCase(CurrencyExchange currencyExchange) {
        return new CreateTransactionsSummaryUseCase(currencyExchange);
    }

    @Bean
    public CurrencyExchange currencyExchange() {
        return (money, currency) -> Money.of(money.asString(), currency);
    }
}
package com.gs5.transactionssummary.adapter.rest;

import com.gs5.transactionssummary.adapter.database.AccountsAdapterSql;
import com.gs5.transactionssummary.adapter.rest.converter.DomainConverter;
import com.gs5.transactionssummary.adapter.rest.converter.TransactionSummaryDtoConverter;
import com.gs5.transactionssummary.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Clock;

@Configuration
public class BankingTransactionsSummaryConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/bankingtransactions/**").allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DomainConverter domainConverter(Clock clock) {
        return new DomainConverter(clock);
    }

    @Bean
    public TransactionSummaryDtoConverter dtoConverter(){
        return new TransactionSummaryDtoConverter();
    }

    @Bean
    public CreateTransactionsSummaryUseCase createTransactionsSummaryUseCase(CurrencyExchange currencyExchange) {
        return new CreateTransactionsSummaryUseCase(currencyExchange);
    }

    @Bean
    public CurrencyExchange currencyExchange() {
        return (money, currency) -> Money.of(money.asString(), currency);
    }

    @Bean
    public AccountsRepository accountsRepository(JdbcTemplate jdbc, Clock clock) {
        return new AccountsAdapterSql(jdbc, clock);
    }

    @Bean
    public GetAllAccountUseCase getAllAccountUseCase(AccountsRepository accountsRepository) {
        return new GetAllAccountUseCase(accountsRepository);
    }

    @Bean
    public UpgradeBalanceForAccountUseCase upgradeBalanceForAccountUseCase(AccountsRepository accountsRepository){
        return new UpgradeBalanceForAccountUseCase(accountsRepository);
    }

}
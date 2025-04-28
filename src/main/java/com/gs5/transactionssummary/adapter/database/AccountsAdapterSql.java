package com.gs5.transactionssummary.adapter.database;

import com.gs5.transactionssummary.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public final class AccountsAdapterSql implements AccountsRepository {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final JdbcTemplate jdbc;
    private final Clock clock;

    public AccountsAdapterSql(JdbcTemplate jdbc, Clock clock) {
        this.jdbc = jdbc;
        this.clock = clock;
    }

    @Override
    public Accounts loadAll() {
        final List<ClientRecord> clientRecords = jdbc.query(
                "SELECT id, name, surname, country FROM clients",
                clientRowMapper()
        );

        final List<Account> accounts = clientRecords.stream()
                .map(this::buildAccount)
                .collect(Collectors.toList());

        return new Accounts(accounts);
    }

    @Override
    public Account upgradeBalanceFor(Account account) {
        final var balance = account.balance();
        final var client = account.client();

        int rowsUpdated = jdbc.update(
                "UPDATE balances SET total = ?, date = ? WHERE client_id = ?",
                balance.getTotal().asString(),
                balance.getDate().asLocalDate(),
                client.getId()
        );

        if (rowsUpdated == 0) {
            throw new IllegalStateException("No balance found for client with ID: " + client.getId());
        }

        return account;
    }

    private Account buildAccount(ClientRecord clientRecord) {
        Balance balance = jdbc.query(
                        "SELECT total, currency, date FROM balances WHERE client_id = ?",
                        balanceRowMapper(),
                        clientRecord.id()
                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No balance found for client_id: " + clientRecord.id()));

        List<BankingTransaction> transactions = jdbc.query(
                "SELECT type, description, date, \"value\", currency FROM transactions WHERE client_id = ?",
                transactionRowMapper(),
                clientRecord.id()
        );

        final Client client = new Client(clientRecord.id(),
                clientRecord.name(),
                clientRecord.surname(),
                clientRecord.country()
        );

        return Account.builder()
                .client(client)
                .balance(balance)
                .transactions(transactions)
                .clock(clock)
                .build();
    }

    private RowMapper<ClientRecord> clientRowMapper() {
        return (rs, rowNum) -> new ClientRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("country")
        );
    }

    private RowMapper<Balance> balanceRowMapper() {
        return (rs, rowNum) -> Balance.from(
                rs.getBigDecimal("total").toString(),
                Currency.valueOf(rs.getString("currency")),
                rs.getDate("date").toLocalDate().format(FORMATTER)
        );
    }

    private RowMapper<BankingTransaction> transactionRowMapper() {
        return (rs, rowNum) -> BankingTransaction.from(
                rs.getString("type"),
                rs.getString("description"),
                rs.getDate("date").toLocalDate().format(FORMATTER),
                rs.getBigDecimal("value").toString(),
                Currency.valueOf(rs.getString("currency"))
        );
    }

    private record ClientRecord(Long id, String name, String surname, String country) {
    }
}


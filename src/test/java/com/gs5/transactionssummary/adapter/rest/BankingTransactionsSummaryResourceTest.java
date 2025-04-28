package com.gs5.transactionssummary.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
class BankingTransactionsSummaryResourceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("getAccountsTestCases")
    public void shouldGetAccounts(String response, HttpStatus httpStatus) throws IOException {
        final var responseExcepted = getJson(response);

        ResponseBodyExtractionOptions body = RestAssured.with()
                .header("Content-Type", "application/json")
                .when()
                .get("/bankingtransactions/accounts")
                .then()
                .assertThat()
                .statusCode(httpStatus.value())
                .extract()
                .body();

        assertJsonEquals(responseExcepted, body);
    }

    private static Stream<Arguments> getAccountsTestCases() {
        return Stream.of(
                of("get_accounts_success_response.json", OK)
        );
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("summaryTestCases")
    public void shouldGenerateSummary(String contractJson, String summaryJson, HttpStatus httpStatus) throws IOException {
        final var contract = getJson(contractJson);
        final var summary = getJson(summaryJson);

        ResponseBodyExtractionOptions body = RestAssured.with()
                .body(contract)
                .header("Content-Type", "application/json")
                .when()
                .post("/bankingtransactions/summary")
                .then()
                .assertThat()
                .statusCode(httpStatus.value())
                .extract()
                .body();

        assertJsonEquals(summary, body);
    }

    private static Stream<Arguments> summaryTestCases() {
        return Stream.of(
                of("transactions_with_same_currencies_request_successful.json", "transactions_with_same_currencies_response_successful.json", OK),
                of("transactions_with_different_currencies_request_successful.json", "transactions_with_same_currencies_response_successful.json", OK),
                of("transactions_with_account_currency_different_when_transactions_request_successful.json", "transactions_with_account_currency_different_when_transactions_response_successful.json", OK),
                of("zero_transactions_request_successful.json", "zero_transactions_response_successful.json", OK),
                of("transactionsSummaryRequest_not_present_request_fail.json", "transactionsSummaryRequest_not_present_response_fail.json", BAD_REQUEST),
                of("not_supported_currency_request_fail.json", "not_supported_currency_response_fail.json", BAD_REQUEST),
                of("not_supported_date_format_request_fail.json", "not_supported_date_format_response_fail.json", BAD_REQUEST)
        );
    }

    private String getJson(String fileName) throws IOException {
        final var directory = "payloads/";
        return new String(BankingTransactionsSummaryResourceTest.class.getClassLoader().getResourceAsStream(directory + fileName).readAllBytes());
    }

    private void assertJsonEquals(String summary, ResponseBodyExtractionOptions body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(Objects.requireNonNull(summary)), mapper.readTree(body.asByteArray()));
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("upgradeAccountBalanceAndDateTestCases")
    public void shouldUpgradeAccountBalanceAndDate(String requestJson, String responseJson, HttpStatus httpStatus) throws IOException {
        final var request = getJson(requestJson);
        final var response = getJson(responseJson);

        ResponseBodyExtractionOptions body = RestAssured.with()
                .body(request)
                .header("Content-Type", "application/json")
                .when()
                .post("/bankingtransactions/create-or-update-accounts")
                .then()
                .assertThat()
                .statusCode(httpStatus.value())
                .extract()
                .body();

        assertJsonEquals(response, body);
    }

    private static Stream<Arguments> upgradeAccountBalanceAndDateTestCases() {
        return Stream.of(
                of("upgrade_account_balance_and_date_request_successful.json", "upgrade_account_balance_and_date_response_successful.json", OK)
        );
    }


    @TestConfiguration
    static class FakeClockConfig {

        @Bean
        public Clock clock() {
            return Clock.fixed(Instant.parse("2020-10-29T10:00:00.00Z"), ZoneOffset.UTC);
        }
    }
}
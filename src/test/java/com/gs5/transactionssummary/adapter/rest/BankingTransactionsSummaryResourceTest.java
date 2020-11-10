package com.gs5.transactionssummary.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
class BankingTransactionsSummaryResourceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void shouldGenerateSummary(String contractJson, String summaryJson, HttpStatus httpStatus) throws IOException {
        val contract = getJson(contractJson);
        val summary = getJson(summaryJson);

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

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("transactions_with_same_currencies_request_successful.json", "transactions_with_same_currencies_response_successful.json", HttpStatus.OK),
                Arguments.of("transactions_with_different_currencies_request_successful.json", "transactions_with_same_currencies_response_successful.json", HttpStatus.OK),
                Arguments.of("transactions_with_account_currency_different_when_transactions_request_successful.json", "transactions_with_account_currency_different_when_transactions_response_successful.json", HttpStatus.OK),
                Arguments.of("zero_transactions_request_successful.json", "zero_transactions_response_successful.json", HttpStatus.OK),
                Arguments.of("transactionsSummaryRequest_not_present_request_fail.json", "transactionsSummaryRequest_not_present_response_fail.json", HttpStatus.BAD_REQUEST),
                Arguments.of("not_supported_currency_request_fail.json", "not_supported_currency_response_fail.json", HttpStatus.BAD_REQUEST),
                Arguments.of("not_supported_date_format_request_fail.json", "not_supported_date_format_response_fail.json", HttpStatus.BAD_REQUEST),
                Arguments.of("not_supported_currency_value_wrong_separator_request_fail.json", "not_supported_currency_value_wrong_separator_response_fail.json", HttpStatus.BAD_REQUEST),
                Arguments.of("not_supported_currency_value_request_fail.json", "not_supported_currency_value_response_fail.json", HttpStatus.BAD_REQUEST)

        );
    }

    private String getJson(String fileName) throws IOException {
        val directory = "payloads/";
        return new String(BankingTransactionsSummaryResourceTest.class.getClassLoader().getResourceAsStream(directory + fileName).readAllBytes());
    }

    private void assertJsonEquals(String summary, ResponseBodyExtractionOptions body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(Objects.requireNonNull(summary)), mapper.readTree(body.asByteArray()));
    }


    @TestConfiguration
    static class FakeClockConfig {

        @Bean
        public Clock clock() {
            return Clock.fixed(Instant.parse("2020-10-29T10:00:00.00Z"), ZoneOffset.UTC);
        }
    }
}
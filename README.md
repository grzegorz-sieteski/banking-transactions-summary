# banking-transactions-summary

# RUN
./mvnw spring-boot:run

#TEST
Request:

curl --location --request POST 'http://localhost:8080/bankingtransactions/summary' \
--header 'Content-Type: application/json' \
--data-raw '{
  "clients": {
    "client": [
      {
        "info": {
          "name": "Tomasz",
          "surname": "Karcznski"
        },
        "balance": {
          "total": "12110",
          "currency": "PLN",
          "date": "01.05.2020"
        },
        "transactions": [
          {
            "type": "income",
            "description": "salary",
            "date": "04.05.2020",
            "value": "7500",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "mortgage",
            "date": "06.05.2020",
            "value": "1100",
            "currency": "PLN"
          },
          {
            "type": "income",
            "description": "interests",
            "date": "10.05.2020",
            "value": "1700",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "11.05.2020",
            "value": "1200",
            "currency": "PLN"
          }
        ]
      },
      {
        "info": {
          "name": "Natalia",
          "surname": "Nowak",
          "country": "Poland"
        },
        "balance": {
          "total": "6750",
          "currency": "PLN",
          "date": "01.05.2020"
        },
        "transactions": [
          {
            "type": "income",
            "description": "salary",
            "date": "04.05.2020",
            "value": "10500",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "10.05.2020",
            "value": "1200",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "11.05.2020",
            "value": "1050,50",
            "currency": "PLN"
          }
        ]
      }
    ]
  }
}'

RESPONSE:

[{"info":{"name":"Tomasz","surname":"Karcznski"},"balance":{"total":"19010,00","currency":"PLN","date":"09.11.2020"},"summary":{"totalExpenses":"2300,00","totalRevenues":"9200,00","totalTurnover":"11500,00","currency":"PLN"}},{"info":{"name":"Natalia","surname":"Nowak","country":"Poland"},"balance":{"total":"14999,50","currency":"PLN","date":"09.11.2020"},"summary":{"totalExpenses":"2250,50","totalRevenues":"10500,00","totalTurnover":"12750,50","currency":"PLN"}}]
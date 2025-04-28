package com.gs5.transactionssummary.adapter.rest.converter;

import com.gs5.transactionssummary.domain.Client;
import gs5.bankingtransactions.summary.model.Info;

public class InfoDtoConverter {
    public static Info convertToInfoDTO(Client client) {
        return new Info()
                .clientId(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .country(client.getCountry().orElse(null));
    }
}

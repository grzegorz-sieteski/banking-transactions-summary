package com.gs5.transactionssummary.adapter.rest;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@AllArgsConstructor
public class CustomErrorAttributes extends DefaultErrorAttributes {
    private final Clock clock;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options){
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("timestamp", LocalDateTime.now(clock));
        return errorAttributes;
    }
}

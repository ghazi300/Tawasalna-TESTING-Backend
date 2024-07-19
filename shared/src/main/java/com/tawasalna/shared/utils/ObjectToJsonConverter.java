package com.tawasalna.shared.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ObjectToJsonConverter implements IObjectToJsonConverter {

    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> toJson(Object from) {
        return this.objectMapper
                .convertValue(from, new TypeReference<>() {
                });
    }
}

package com.jumkid.garage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumkid.share.service.dto.GenericDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloneUtil {

    public static <T extends GenericDTO> T clone(T obj) {
        try {
            return deepClone(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to clone object {}", e.getMessage());
        }
        return null;
    }

    private static <T extends GenericDTO> T deepClone(T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (T) objectMapper.readValue(objectMapper.writeValueAsString(obj), obj.getClass());
    }
}

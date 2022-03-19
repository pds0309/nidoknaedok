package com.dto.common;

import com.errors.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * REST 응답을 위한 객체이다.
 */
public class ResponseDTO {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseDTO() {
        //
    }

    private static <T> boolean isNull(T data) {
        return data == null;
    }

    private static <T> boolean isArray(T data) {
        return data instanceof List;
    }

    public static <T> JSONObject toJson(int status, T data) throws JsonProcessingException {
        if (isNull(data)) {
            return new JSONObject()
                    .put("data", new JSONObject())
                    .put("status", status);
        }
        if (isArray(data)) {
            return new JSONObject()
                    .put("data", new JSONArray(objectMapper.writeValueAsString(data)))
                    .put("status", status);
        }
        return new JSONObject()
                .put("data", new JSONObject(objectMapper.writeValueAsString(data)))
                .put("status", status);
    }

    public static JSONObject toJson(ErrorResponse errorResponse) throws JsonProcessingException {
        return new JSONObject()
                .put("error", new JSONObject(objectMapper.writeValueAsString(errorResponse)));
    }
}

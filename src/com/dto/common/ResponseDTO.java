package com.dto.common;

import com.errors.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

/**
 * REST 응답을 위한 객체이다.
 */
public class ResponseDTO<T> {

    private ResponseDTO() {
        //
    }

    public static <T> JSONObject toJson(int status, T data) throws JsonProcessingException {
        return new JSONObject()
                .put("data", new JSONObject(new ObjectMapper().writeValueAsString(data)))
                .put("status", status);
    }

    public static JSONObject toJson(ErrorResponse errorResponse) throws JsonProcessingException {
        return new JSONObject()
                .put("error", new JSONObject(new ObjectMapper().writeValueAsString(errorResponse)));
    }
}

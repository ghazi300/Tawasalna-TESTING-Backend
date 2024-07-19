package com.tawasalna.shared.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    private Integer status;

    public static ApiResponse ofError(String error, Integer code) {
        return new ApiResponse(null, error, code);
    }

    public static ApiResponse ofSuccess(String success, Integer code) {
        return new ApiResponse(success, null, code);
    }
}

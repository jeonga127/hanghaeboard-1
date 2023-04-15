package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDTO<D> {
    private boolean result;
    private String message;
    private D date;

    public static <D> ResponseDTO<D> setSuccess(String message, D data){
        return ResponseDTO.set(true, message, data);
    }

    public static <D> ResponseDTO<D> setFailed(String message){
        return ResponseDTO.set(false, message, null);
    }
}

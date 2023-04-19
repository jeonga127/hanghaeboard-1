package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "add")
public class ResponseDTO<D> {
    private String message;
    private Object data;


    public static <D> ResponseDTO<D> setSuccess(String message, D data){
        return ResponseDTO.add(message, data);
    }

    public static <D> ResponseDTO<D> setBadRequest(String message){
        return ResponseDTO.add( message, null);
    }
}

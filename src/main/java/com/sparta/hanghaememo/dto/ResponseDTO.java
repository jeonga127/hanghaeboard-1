package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "add")
public class ResponseDTO<T> {
    private String message;
    private StatusEnum status;
    private T data;


    public static <T> ResponseDTO<T> setSuccess(String message, StatusEnum status, T data){
        return ResponseDTO.add(message, status, data);
    }

    public static <T> ResponseDTO<T> setFail(String message, StatusEnum status){
        return ResponseDTO.add( message, status, null);
    }
}

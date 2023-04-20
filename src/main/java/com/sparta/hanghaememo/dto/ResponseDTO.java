package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "add")
public class ResponseDTO<D> {
    private String message;
    private StatusEnum status;
    private Object data;


    public static <D> ResponseDTO<D> setSuccess(String message, StatusEnum status, D data){
        return ResponseDTO.add(message, status, data);
    }

    public static <D> ResponseDTO<D> setFail(String message, StatusEnum status){
        return ResponseDTO.add( message, status, null);
    }
}

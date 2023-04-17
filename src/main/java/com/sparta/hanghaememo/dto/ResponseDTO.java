package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDTO<D> {
    private boolean result;
    private String message;
    private StatusEnum status;
    private D data;


    public static <D> ResponseDTO<D> setSuccess(String message, D data){
        return ResponseDTO.set(true, message, StatusEnum.OK, data);
    }

    public static <D> ResponseDTO<D> setFailed(String message){
        return ResponseDTO.set(false, message, StatusEnum.BAD_REQUEST, null);
    }

    public ResponseDTO(String message, StatusEnum status, D data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}

package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.StatusEnum;
import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private StatusEnum status;
    private Object data;


    public ResponseDTO(String message, StatusEnum status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}

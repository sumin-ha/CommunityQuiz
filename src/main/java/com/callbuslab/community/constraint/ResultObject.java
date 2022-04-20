package com.callbuslab.community.constraint;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * Response 에서 사용하는 객체 클래스
 */
public class ResultObject {

    private HttpStatus status;
    private String message;
    private Object data;

    @Builder
    public ResultObject(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

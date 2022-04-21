package com.callbuslab.community.constraint;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Response 에서 사용하는 객체 클래스
 */
@Getter
@NoArgsConstructor
public class ResultObject {

    // HttpStatus
    private HttpStatus status;
    // 응답 메세지
    private String message;
    // 응답 객체
    private Object data;

    @Builder
    public ResultObject(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

package com.callbuslab.community.constraint;

/**
 * 논리 삭제를 위한 플래그 정의
 *
 * 1 : 삭제되지 않은 상태
 * 0 : 삭제 됨
 */
public enum DeleteFlag {

    NOT_DELETE("1"),
    DELETE("0");

    public String deleteFlag;

    DeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }
}

package com.callbuslab.community.constraint;

/**
 * 계정 타입 정의
 */
public enum MemberCategory {

    LESSOR("임대인"),
    REALTOR("공인중개사"),
    LESSEE("임차인"),
    OTHER("외부 사용자");

    private String category;

    MemberCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return category;
    }
}

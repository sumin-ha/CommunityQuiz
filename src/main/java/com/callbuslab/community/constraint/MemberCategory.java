package com.callbuslab.community.constraint;

public enum MemberCategory {

    LESSOR("임대인"),
    REALTOR("공인 중개사"),
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

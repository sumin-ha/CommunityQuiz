package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 커뮤니티 이용객 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Member extends BaseEntity {
    // 커뮤니티 회원 저장 엔티티

    // 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 닉네임
    @Column(nullable = false)
    private String nickName;
    // 계정 타입
    @Column(nullable = false)
    private String accountType;
    // 계정 id로서 인증값
    @Column(nullable = false)
    private String accountId;
    // 탈퇴여부
    private String quit;


}

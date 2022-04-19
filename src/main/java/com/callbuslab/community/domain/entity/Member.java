package com.callbuslab.community.domain.entity;

import com.callbuslab.community.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 커뮤니티 이용객 엔티티
 */
@NoArgsConstructor
@Entity
@Getter
public class Member extends BaseEntity {

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

    // 탈퇴 시간
    @Column
    private LocalDateTime quitDate;

    // 탈퇴여부
    @Column
    private String quit;

    @Builder
    public Member(String nickName, String accountType, String accountId, String quit) {
        this.nickName = nickName;
        this.accountType = accountType;
        this.accountId = accountId;
        this.quit = quit;
    }
}

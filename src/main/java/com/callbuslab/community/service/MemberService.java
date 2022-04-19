package com.callbuslab.community.service;

import com.callbuslab.community.constraint.MemberCategory;
import com.callbuslab.community.domain.entity.Member;
import com.callbuslab.community.domain.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 구성원 관련 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 화면상에 표시할 멤버 이름 습득하는 메서드
    // 이름의 형식은 닉네임(계정타입의 한글명)
    public String getMemberName(Long memberId) {
        // 멤버 습득
        Member member = memberRepository.getById(memberId);

        // 멤버의 계정 타입 한글명 습득
        String memberCategoryName = "";
        try {
            memberCategoryName = MemberCategory.valueOf(member.getAccountType()).getCategoryName();
        } catch(Exception e) {
            memberCategoryName = MemberCategory.OTHER.getCategoryName();
        }

        // 멤버의 이름생성
        StringBuilder memberName = new StringBuilder();
        memberName.append(member.getNickName());
        memberName.append("(");
        memberName.append(memberCategoryName);
        memberName.append(")");

        // 생성한 이름 반환
        return memberName.toString();
    }
}

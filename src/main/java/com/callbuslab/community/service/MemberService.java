package com.callbuslab.community.service;

import com.callbuslab.community.constraint.MemberCategory;
import com.callbuslab.community.constraint.ResponseMessage;
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

    // 닉네임 등록
    // 커뮤니티용 멤버 테이블에 등록.
    // 커뮤니티의 회원테이블에는 에는 임대인, 임차인, 공인중계사만 등록 가능함.
    public String registerNickName(String nickName, String token) {

        // 전송받은 Authorization을 해석
        if(token == null || token == "") {
            // Authorization가 비어있을 경우엔 외부 사용자로 취급. 등록하지 않음.
            return ResponseMessage.memberRegisterAuthError;
        }
        String[] split = token.split(" ");

        // 중복 등록 체크
        Member checkMember = memberRepository.findByAccountId(token);
        if(checkMember != null) {
            return ResponseMessage.memberDuplicateError;
        }

        // 멤버 등록
        Member member = Member.builder().nickName(nickName)
                .accountType(split[0])
                .accountId(token)
                .quit("1")
                .build();
        memberRepository.save(member);

        return ResponseMessage.successMessage;
    }

    // Authorization으로 멤버 ID 찾기
    // 닉네임 등록이 끝난 멤버의 ID를 찾음.
    public Long getMemberId(String token) {

        // 전송받은 Authorization을 이용해 체크
        if(token == null || token == "") {
            // Authorization가 비어있을 경우엔 외부 사용자로 취급.
            // MemberId로서 존재하지 않는 id를 돌려주어, 처리가 실행되지 않도록 함.
            return Long.MIN_VALUE;
        }

        // 등록 된 멤버 정보 찾기
        Member checkMember = memberRepository.findByAccountId(token);
        if(checkMember == null) {
            // 닉네임 미등록자도 존재하지 않는 id를 돌려주어, 처리가 실행되지 않도록 함.
            return Long.MIN_VALUE;
        } else {
            return checkMember.getId();
        }
    }
}

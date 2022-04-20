package com.callbuslab.community.constraint;

public class ResponseMessage {
    public static final String successMessage = "요청 처리 완료";
    public static final String favoriteRegisterFail = "댓글 등록 실패";
    public static final String boardRegisterFail = "글 등록 실패";
    public static final String boardUpdateFail = "글 수정 실패";
    public static final String boardDeleteFail = "글 삭제 실패";
    public static final String commentRegisterFail = "댓글 등록 실패";
    public static final String commentUpdateFail = "댓글 수정 실패";
    public static final String commentDeleteFail = "댓글 삭제 실패";
    public static final String authError = "처리 할 수 없는 요청입니다.";
    public static final String memberRegisterAuthError = "임차인, 임대인, 공인중계사 외엔 등록 할 수 없습니다.";
    public static final String memberDuplicateError = "이미 닉네임을 등록하셨습니다.";
    public static final String memberNotFoundError = "외부 사용자이거나 닉네임을 등록하지 않으셨습니다. 확인해주세요.";
}

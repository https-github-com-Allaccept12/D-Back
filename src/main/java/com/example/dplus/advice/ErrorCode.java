package com.example.dplus.advice;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 로그인 및 권한 관련에러
    NO_USER_ERROR(404, "A001","해당 유저를 찾을 수 없습니다."),

    NO_AUTHENTICATION_ERROR(401, "A002","로그인 사용자만 사용할 수 있습니다."),
    TOKEN_EXPIRATION_ERROR(400,"A003","로그인 정보가 만료되었습니다."),
    NO_TOKEN_ERROR(400, "A004","토큰이 존재하지 않습니다."),
    NO_AUTHORIZATION_ERROR(403,"A005","접근 권한이 없습니다."),
    NO_MATCH_ITEM_ERROR(400, "A006","일치하는 토큰값이 없습니다."),
    EXPIRED_JWT_ERROR(444,"A007", "기존 토큰이 만료되었습니다."),

    // Validation 체크 에러
    ALREADY_NICKNAME_ERROR(400, "V001","이미 사용중인 닉네임입니다."),

    PHOTO_UPLOAD_ERROR(400, "V002","작품 업로드는 섬네일포함 최소 두개를 해주세요."),
    BAD_CONDITION_NICKNAME_ERROR(400, "V003","닉네임 조건에 맞지 않습니다."),
    CONVERTING_FILE_ERROR(400,"V004","파일 변환 중 에러가 발생했습니다"),

    // 아트워크, 포스트 게시글 페이지 에러
    NONEXISTENT_ERROR(404,"AP001","존재하지 않는 게시글 및 작업물입니다"),
    DAILY_WRITE_UP_BURN_ERROR(400, "AP002",
            "일일 작품 작성 가능분을 다 사용 하셨습니다. 작성 가능 횟수는 오전 00시에 초기화 됩니다."),
    DAILY_POST_WRITE_UP_BURN_ERROR(400, "AP003",
            "일일 작품 작성 가능분을 다 사용 하셨습니다. 작성 가능 횟수는 오전 00시에 초기화 됩니다."),
    ALREADY_SELECTED_ERROR(400, "AP004","이미 채택된 글입니다."),
    NON_KEYWORD_ERROR(400, "AP005","검색어를 입력해 주세요."),

    // 좋아요, 북마크, 팔로우 에러
    NO_BOOKMARK_MY_POST_ERROR(400,"LBF001","자신의 게시물은 북마크할 수 없습니다."),
    ALREADY_BOOKMARK_ERROR(400, "LBF002","이미 북마크/북마크해지한 글입니다."),
    EXIST_FOLLOW_ERROR(400,"LBF003","이미 팔로우/언팔로우한 사람입니다."),
    ALREADY_LIKE_ERROR(400, "LBF004","이미 좋아요/좋아요 해지한 글입니다.");

    private int statusCode;
    private final String code;
    private final String message;
}

package TeamDPlus.code.advice;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NO_USER_ERROR("해당 유저를 찾을 수 없습니다."),
    NO_AUTHENTICATION_ERROR("로그인 사용자만 사용할 수 있습니다."),
    NO_TOKEN_ERROR("토큰이 존재하지 않습니다."),
    NO_MATCH_ITEM_ERROR("일치하는 값이 없습니다."),
    ALREADY_NICKNAME_ERROR("이미 사용중인 닉네임입니다."),
    NO_BOOKMARK_MY_POST_ERROR("자신의 게시물은 북마크할 수 없습니다."),
    ALREADY_BOOKMARK_ERROR("이미 북마크/북마크해지한 글입니다."),
    TOKEN_EXPIRATION_ERROR("로그인 정보가 만료되었습니다."),
    DAILY_WRITE_UP_BURN_ERROR("일일 작성 가능분을 다 사용 하셨습니다. 작성 가능 횟수는 오전 00시에 초기화 됩니다."),
    PHOTO_UPLOAD_ERROR("작품을 업로드 해주세요."),
    NONEXISTENT_ERROR("존재하지 않는 게시글 및 작업물"),
    NO_AUTHORIZATION_ERROR("접근 권한이 없습니다."),
    EXIST_FOLLOW_ERROR("이미 팔로우/언팔로우한 사람입니다."),
    BAD_CONDITION_NICKNAME_ERROR("닉네임 조건에 맞지않습니다."),
    CONVERTING_FILE_ERROR("파일 변환 에러"),
    ALREADY_LIKE_ERROR("이미 좋아요/좋아요 해지한 글입니다."),
    ALREADY_SELECTED_ERROR("이미 채택된 글입니다.");

    private final String message;
}

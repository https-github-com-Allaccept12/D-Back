package TeamDPlus.code.dto.response;

public class FollowResponseDto {


    public static class FollowList {
        private Long account_id;
        private String account_img;

        public FollowList(final Long account_id,final String account_img) {
            this.account_id = account_id;
            this.account_img = account_img;
        }
    }

}


package TeamDPlus.code.jwt.provider;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getProfileImg();
}

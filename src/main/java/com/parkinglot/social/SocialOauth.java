package com.parkinglot.social;

public interface SocialOauth {
	
	String getOauthRedirectURL();
	
	String requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof AppleOauth) {
            return SocialLoginType.APPLE;
        }  else if (this instanceof KakaoOauth) {
            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }
}
package web.model;

public class RefreshJwtRequest {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public RefreshJwtRequest setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}

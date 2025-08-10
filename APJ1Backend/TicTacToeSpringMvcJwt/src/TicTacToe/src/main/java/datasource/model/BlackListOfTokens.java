package datasource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BlackListOfTokens {

    @Id @GeneratedValue private int uuid;

    private String token;

    public BlackListOfTokens() {}

    public BlackListOfTokens(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package datasource.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_table")
public class S21User {

    private @Id @GeneratedValue int uuid;
    private  String login;
    private  String password;

    public S21User() {}

    public S21User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }
    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

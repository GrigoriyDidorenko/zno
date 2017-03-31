package ua.com.zno.online.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by quento on 26.03.17.
 */

@Entity
@Table(catalog = "zno", name = "users")
public class User extends AbstractEntity {

    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "secret", nullable = false)
    private String secret;
    @Column(name = "email", nullable = false)
    private String email;

    public User() {
    }

    public User(String email, String secret){
        this.email = email;
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

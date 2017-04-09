/*
package ua.com.zno.online.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

*/
/**
 * Created by quento on 26.03.17.
 *//*


@Entity
@Table(catalog = "zno", name = "users")
public class User extends AbstractEntity {

    @Column
    private String name;
    @Column
    private String surname;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String secret;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private Boolean enabled;

    public User() {
    }

    public User(String login, String secret){
        this.login = login;
        this.secret = secret;
    }

    public User(String name, String surname, String login, String email, String secret, Boolean enabled) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.secret = secret;
        this.email = email;
        this.enabled = enabled;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
*/

package ua.com.zno.online.dto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.dto.AbstractDTO;
import ua.com.zno.online.util.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * Created by obalitskiy on 4/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("user")
public class UserDTO extends AbstractDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    @NotNull
    private String password;

    @JsonProperty("user_id")
    private String login;
    @JsonProperty("email")
    @NotNull
    @Pattern(regexp = Constants.EMAIL_VALIDATOR)
    private String email;
    @JsonProperty(value = "access_token", access = JsonProperty.Access.WRITE_ONLY) //FIXME we do need this for vk authorization, if we change the way vk authorization works, we should use password field except of access_token to prevent duplicates
    private String secret;

    public UserDTO() {
    }

    public UserDTO(String login, String email, String secret) {
        this.login = login;
        this.email = email;
        this.secret = secret;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by obalitskiy on 4/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("user")
public class UserDTO extends AbstractDTO{
    @JsonProperty("user_id")
    private String vkId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("access_token")
    private String secret;

    public UserDTO() {
    }

    public UserDTO(String login, String email, String secret) {
        this.vkId = login;
        this.email = email;
        this.secret = secret;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
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
}

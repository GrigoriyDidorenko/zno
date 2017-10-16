package ua.com.zno.online.dto.security;

/**
 * Created by g.didorenko on 16.09.17.
 */
public class UserCredentialsDTO {

    private String email;
    private String oldPassword;
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

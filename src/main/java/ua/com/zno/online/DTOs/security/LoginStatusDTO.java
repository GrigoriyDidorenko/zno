package ua.com.zno.online.DTOs.security;

/**
 * Created by g.didorenko on 16.09.17.
 */
public class LoginStatusDTO {

    private final boolean loggedIn;
    private final String username;
    private String failReason;

    public LoginStatusDTO(boolean loggedIn, String username) {
        this.loggedIn = loggedIn;
        this.username = username;
    }

    public LoginStatusDTO(boolean loggedIn, String username, String failReason) {
        this.loggedIn = loggedIn;
        this.username = username;
        this.failReason = failReason;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getFailReason() {
        return failReason;
    }
}

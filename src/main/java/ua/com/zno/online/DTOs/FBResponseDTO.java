package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mac on 18.06.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("data")
public class FBResponseDTO {
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("application")
    private String application;
    @JsonProperty("is_valid")
    private String isValid;
    @JsonProperty("user_id")
    private String userId;

    public FBResponseDTO() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

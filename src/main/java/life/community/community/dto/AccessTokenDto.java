package life.community.community.dto;


import lombok.Data;
import java.io.Serializable;


/**
 * 用来向Github请求access token时要携带的信息
 */
@Data
public class AccessTokenDto implements Serializable {
    /**
     * OAtuh APP的Client ID
     */
    private String client_id;
    /**
     * OAuth APP的client secret
     */
    private String client_secret;
    /**
     * 用户授权成功后由Github返回的code
     */
    private String code;
    /**
     * 授权成功后跳转的URL，和Github OAutho APP上填的一致
     */
    private String redirect_uri;
    /**
     * 用户授权成功后由Github返回的state
     */
    private String state;
}

package life.community.community.provider;


import com.alibaba.fastjson.JSON;
import life.community.community.dto.AccessTokenDto;
import life.community.community.dto.GitHubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
public class GithubProvider {

    /**
     * 获取用户的access token
     * @param accessTokenDto    获取access token的必要参数的类
     * @return                  返回access token
     */
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 根据access token获取用户信息
     * @param accessToken       access token
     * @return                  返回的用户信息（后期可以添加）
     */
    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String userInfo = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(userInfo, GitHubUser.class);
            return  gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

}

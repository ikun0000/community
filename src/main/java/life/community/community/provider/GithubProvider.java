package life.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.community.community.dto.AccessTokenDto;
import life.community.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithubProvider {

    // 获取Github的access_token
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
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 获取用户信息
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
        }

        return null;
    }

}

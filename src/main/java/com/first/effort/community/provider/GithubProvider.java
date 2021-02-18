package com.first.effort.community.provider;

import com.alibaba.fastjson.JSON;
import com.first.effort.community.dto.AccessTokenDTO;
import com.first.effort.community.dto.GithubUser;
import okhttp3.*;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

import static okhttp3.RequestBody.*;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");//括号内为定义的返回类型
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token="+accessToken)
//                .build();
                .url("https://api.github.com/user?access_token=" + accessToken)
                .header("Authorization","token "+accessToken)
                .build();

        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }

}

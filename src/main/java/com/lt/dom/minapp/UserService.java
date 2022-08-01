package com.lt.dom.minapp;


import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.ResultPhoneAuth;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UserService {


    //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;

    @GET("/cgi-bin/token?grant_type=client_credential")
    public Call<List<PhoneAuth>> getToken(
      @Query("appid") String appid,
      @Query("secret") String secret);

    //@Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/core/api/v2/phoneAuth")


    public Call<ResultPhoneAuth> getUser(@Query("token") String token, @Body PhoneAuth requestBody);
}
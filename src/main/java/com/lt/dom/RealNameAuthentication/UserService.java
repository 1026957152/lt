package com.lt.dom.RealNameAuthentication;


import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {

    @GET("/users")
    public Call<List<PhoneAuth>> getUsers(
      @Query("per_page") int per_page, 
      @Query("page") int page);

    //@Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/core/api/v2/phoneAuth")
    public Call<ResultPhoneAuth> getUser(@Query("token") String token,@Body PhoneAuth requestBody);
}
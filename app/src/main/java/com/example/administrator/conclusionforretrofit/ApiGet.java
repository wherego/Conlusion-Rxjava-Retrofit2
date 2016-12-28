package com.example.administrator.conclusionforretrofit;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface ApiGet {
    /*
    * Get方法
    * */
    @GET("getlogin")
    Observable<UserInfo> getParse(@Query("name") String name);
}

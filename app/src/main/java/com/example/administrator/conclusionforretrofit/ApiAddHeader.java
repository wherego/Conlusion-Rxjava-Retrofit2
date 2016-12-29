package com.example.administrator.conclusionforretrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/29.
 */

public interface ApiAddHeader {
    /*
    * 添加头部信息方法1
    * */
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-your-App"})
    @GET("addHeader")
    Observable<String> getParse(@Query("name") String name);
}

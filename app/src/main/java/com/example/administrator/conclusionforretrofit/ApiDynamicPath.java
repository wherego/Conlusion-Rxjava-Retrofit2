package com.example.administrator.conclusionforretrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface ApiDynamicPath {
    /*
   * 动态设置服务器接口的方法
   * */
    @GET("{url}")
    Observable<String> getParse(@Path("path") String path ,@Query("username") String username);
}

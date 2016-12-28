package com.example.administrator.conclusionforretrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface ApiPost {
    /*
    * Post方法
    * */
    @POST("uploadimg")
    Observable<Imgs> getParse(@PartMap() Map<String , RequestBody> map , @Part("key") String key ,@Part() RequestBody userimg);
}

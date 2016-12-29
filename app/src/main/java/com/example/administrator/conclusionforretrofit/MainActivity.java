package com.example.administrator.conclusionforretrofit;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*
* Rxjava+Retrofit
*
* Retrofit可用转换器：
*
* Gson: com.squareup.retrofit2:converter-gson(Json)
*
* Jackson: com.squareup.retrofit2:converter-jackson(Json etc)
*
* Moshi: com.squareup.retrofit2:converter-moshi
*
* Protobuf: com.squareup.retrofit2:converter-protobuf(Protobuf)
*
* Wire: com.squareup.retrofit2:converter-wire(Wire)
*
* Simple XML: com.squareup.retrofit2:converter-simplexml(XML)
*
* Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars(String)
* */
public class MainActivity extends AppCompatActivity {

    private Button vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vt = (Button) findViewById(R.id.vt);
        vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGet();
            }
        });
    }

    /*
    * Get方法
    * */
    public void doGet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.12:8080/xxxxx")//服务器地址
                .addConverterFactory(GsonConverterFactory.create())//添加数据格式转换工具(Resonse----->Json)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava的适配工具
                .build();
        ApiGet apiGet = retrofit.create(ApiGet.class);
        Observable<UserInfo> observable = apiGet.getParse("请求参数");//参数
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*
    * Post方法(表单上传,多图片加参数)
    *
    * */
    public void doPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.12:8080/xxxxx")//服务器地址
                .addConverterFactory(GsonConverterFactory.create())//添加数据格式转换工具(Resonse----->Json)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava的适配工具
                .build();
        File file1 = new File(Environment.getExternalStorageDirectory(), "01.jpg");//获取图片
        File file2 = new File(Environment.getExternalStorageDirectory(), "02.jpg");
        File userImgFile = new File(Environment.getExternalStorageDirectory(), "userImg.jpg");
        RequestBody photo1 = RequestBody.create(MediaType.parse("image/jpg"), file1);//创建图片请求体
        RequestBody photo2 = RequestBody.create(MediaType.parse("image/jpg"), file2);
        RequestBody userImg = RequestBody.create(MediaType.parse("image/jpg"), userImgFile);//单独图片上传的情况
//        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "json数据");//用于Json上传
        Map<String, RequestBody> photos = new HashMap<>();
        photos.put("photos\"; filename=\"图片名1.png", photo1);//photos为服务器对应key，filename是服务器获取到的文件名
        photos.put("photos\"; filename=\"图片名2.png", photo2);
        ApiPost apiPost = retrofit.create(ApiPost.class);
        Observable<Imgs> observable = apiPost.getParse(photos, "key参数", userImg);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Imgs>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Imgs response) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*
    * 动态设置服务器接口的方法
    * */
    public void dynamicPath() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.12:8080/xxxxx")//服务器地址
                .addConverterFactory(ScalarsConverterFactory.create())//添加数据格式转换工具(Resonse----->String)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava的适配工具
                .build();
        ApiDynamicPath apiDynamicPath = retrofit.create(ApiDynamicPath.class);
        Observable<String> observable = apiDynamicPath.getParse("服务器对应接口Path", "请求参数");//参数
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String response) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*
    * 统一添加请求头方法2(单一API添加请求头方法1见ApiAddHeader.class)------
    * */
    public void addHeader() {
        /*
        * 添加log
        **/
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        /*
        * 添加统一的header
        * */
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("mac", "f8:00:ea:10:45")
                        .addHeader("uuid", "gdeflatfgfg5454545e")
                        .addHeader("userId", "Fea2405144")
                        .addHeader("netWork", "wifi")
                        .build();
                return chain.proceed(request);
            }
        }).addInterceptor(logging)//添加log
                .build();
        //----------------------------------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.12:8080/xxxxx")//服务器地址
                .addConverterFactory(ScalarsConverterFactory.create())//添加数据格式转换工具(Resonse----->String)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava的适配工具
                .client(client)//--------------添加OkHttpClient
                .build();
        ApiAddHeader apiAddHeader = retrofit.create(ApiAddHeader.class);
        Observable<String> observable = apiAddHeader.getParse("请求参数");//参数
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String response) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}

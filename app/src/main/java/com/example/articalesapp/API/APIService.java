package com.example.articalesapp.API;

import com.example.articalesapp.Data.models.dataresponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9")
    Observable<dataresponse> getAllData1();
    @GET("v1/articles?source=associated-press&apiKey=533af958594143758318137469b41ba9")
    Observable<dataresponse> getAllData2();


}

package com.muyouzhi.pixabayrxjava.network;


import com.muyouzhi.pixabayrxjava.ImageDetails;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabeyApi {
    @GET("api/")
    Observable<ResponseBody> getImageResult(@Query("key") String key, @Query("q") String keyword);
}

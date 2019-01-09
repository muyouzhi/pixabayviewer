package com.muyouzhi.pixabayrxjava.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.muyouzhi.pixabayrxjava.ImageDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ImageDetailsViewModel extends ViewModel {
    private MutableLiveData<List<ImageDetails>> imageDetails;

    public LiveData<List<ImageDetails>> getImageDetailsList() {
        if (imageDetails == null) {
            imageDetails = new MutableLiveData<List<ImageDetails>>();
        }
        return imageDetails;
    }

    public void loadData(String keyword) {
        Log.i("MainActivity", "Loading query: " + keyword);
        List<ImageDetails> data = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PixabayNetworkUtils.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        PixabeyApi pixabayApi = retrofit.create(PixabeyApi.class);
        Observable<ResponseBody> observable =
                pixabayApi.getImageResult(PixabayNetworkUtils.KEY, keyword);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e("MainActivity", "lalala");
                        try {
                            String jsonString = responseBody.string();
                            List<ImageDetails> list = PixabayNetworkUtils
                                    .createImageDetailsFromJson(jsonString);
                            imageDetails.setValue(list);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }


}

package com.muyouzhi.pixabayrxjava;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muyouzhi.pixabayrxjava.network.ImageDetailsViewModel;
import com.muyouzhi.pixabayrxjava.network.PixabayNetworkUtils;
import com.muyouzhi.pixabayrxjava.network.PixabeyApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText mQueryEditBox;
    private Button mSearchButton;

    private RecyclerView mImageRecyclerView;
    private ImageListAdapter mImageListAdapter;
    private ImageDetailsViewModel mImageListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueryEditBox = (EditText) findViewById(R.id.et_keyword);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mImageRecyclerView = (RecyclerView) findViewById(R.id.rv_imagelist);

        mImageListAdapter = new ImageListAdapter(this);
        mImageRecyclerView.setAdapter(mImageListAdapter);
        mImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mImageListViewModel = ViewModelProviders.of(this).get(ImageDetailsViewModel.class);
        mImageListViewModel.getImageDetailsList().observe(this, new android.arch.lifecycle.Observer<List<ImageDetails>>() {
            @Override
            public void onChanged(@Nullable List<ImageDetails> imageDetails) {
                mImageListAdapter.setData(imageDetails);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerSeachButtonListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearchButton.setOnClickListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "OnDestroy");
    }

    private void registerSeachButtonListener() {
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = mQueryEditBox.getText().toString();
                Log.i("MainActivity", keyword);
                mImageListViewModel.loadData(keyword);
            }
        });
    }

    private void loadData(String keyword) {
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
                            mImageListAdapter.setData(list);
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

package com.muyouzhi.pixabayrxjava;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends AppCompatActivity {
    private String mData;
    private ImageView imageView;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.image_details);
        mData = getIntent().getStringExtra(ImageDetails.PARCELABLE_KEY);
        imageView = (ImageView) findViewById(R.id.im_details);
    }

    @Override
    public void onStart(){
        super.onStart();
        Picasso.get().load(mData).into(imageView);
    }



}

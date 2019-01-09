package com.muyouzhi.pixabayrxjava;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder> {
    private List<ImageDetails> imageList = new ArrayList<>();
    private Context context;

    public ImageListAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LinearLayout view = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ImageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder imageListViewHolder, int i) {
        imageListViewHolder.bind(imageList.get(i));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setData(List<ImageDetails> list) {
        imageList = list;
        notifyDataSetChanged();
    }

    public class ImageListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;
        public ImageListViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.im_preview);
            textView = view.findViewById(R.id.tv_user);
            view.setOnClickListener(this);
        }

        void bind(ImageDetails details) {
            Picasso.get().load(details.getPreviewUrl()).into(imageView);
            textView.setText(details.getUser());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ImageDetailActivity.class);
            intent.putExtra(ImageDetails.PARCELABLE_KEY, imageList.get(getAdapterPosition()).getPreviewUrl());
            context.startActivity(intent);
        }
    }
}

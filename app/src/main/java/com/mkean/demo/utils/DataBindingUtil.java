package com.mkean.demo.utils;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DataBindingUtil {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (url != null && !url.equals("")) {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }
    }
}

package com.twc.afview.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtils {
    public static void showImage(Context context, String url, ImageView imageView, int imageId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(imageId);
        options.error(imageId);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerCrop();
        options.dontAnimate();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

}

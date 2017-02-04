package com.lihonghui.vinci.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import net.qiujuer.genius.blur.StackBlur;

/**
 * Created by yq05481 on 2016/11/30.
 */

public class ImageLoader {
    public static void loadImage(Activity activity, String url, ImageView imageView){
        Glide.with(activity).load(url).fitCenter().into(imageView);
    }
    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
    public static void loadImage(Fragment fragment, String url,ImageView imageView){
        Glide.with(fragment).load(url).fitCenter().into(imageView);
    }
    public static void loadBlurImage(Fragment fragment, String url,ImageView imageView,int radius){
        Glide.with(fragment).load(url).transform(new BlurTransformation(fragment.getContext(),radius)).into(imageView);
    }

    static class BlurTransformation extends BitmapTransformation{
        private int radius;
        public BlurTransformation(Context context,int radius) {
            super(context);
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return StackBlur.blurNativelyPixels(toTransform, radius, false);
        }

        @Override
        public String getId() {
            return "blur";
        }
    }
}

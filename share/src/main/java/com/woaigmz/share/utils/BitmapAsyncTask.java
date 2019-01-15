package com.woaigmz.share.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static com.woaigmz.share.utils.Utils.md5;


/**
 * Created by haoran on 2018/8/3.
 */

public class BitmapAsyncTask extends AbstractAsyncTask<Bitmap> {

    private DiskLruCache mDiskLruCache;
    private String urlStr;
    private OnBitmapListener listener;

    public BitmapAsyncTask(Context context, String urlStr, OnBitmapListener listener) {
        this.urlStr = urlStr;
        this.listener = listener;
        try {
            File cacheFile;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                cacheFile = context.getExternalFilesDir("bitmap");
            } else {
                cacheFile = context.getCacheDir();
            }
            mDiskLruCache = DiskLruCache.open(cacheFile, 1, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Bitmap doLoadData() throws Exception {

        String key = md5(urlStr);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);

        Log.w("BitmapTask", urlStr + " snapshot:" + (snapshot == null) + " key:" + key);
        if (snapshot == null) {

            DiskLruCache.Editor editor = mDiskLruCache.edit(key);

            URL url = new URL(urlStr);
            InputStream is = url.openStream();

            Bitmap bitmap = getSampleBitmap(is, 640, 640);
            is.close();

            OutputStream out = editor.newOutputStream(0);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            try {
                out.flush();
                out.close();
                editor.commit();
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                editor.abort();
                mDiskLruCache.flush();
            }
            return bitmap;
        } else {
            InputStream is = snapshot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }
    }

    private Bitmap getSampleBitmap(InputStream is, int width, int height) {

        BufferedInputStream stream = new BufferedInputStream(is);
        stream.mark(4 * 1024);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        calculateInSampleSize(width, height, options, true);
        try {
            stream.reset();
        } catch (IOException e) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        return bitmap;
    }

    private static void calculateInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options, boolean centerInside) {
        calculateInSampleSize(reqWidth, reqHeight, options.outWidth, options.outHeight, options,
                centerInside);
    }

    private static void calculateInSampleSize(int reqWidth, int reqHeight, int width, int height,
                                              BitmapFactory.Options options, boolean centerInside) {
        int sampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio;
            final int widthRatio;
            if (reqHeight == 0) {
                sampleSize = (int) Math.floor((float) width / (float) reqWidth);
            } else if (reqWidth == 0) {
                sampleSize = (int) Math.floor((float) height / (float) reqHeight);
            } else {
                heightRatio = (int) Math.floor((float) height / (float) reqHeight);
                widthRatio = (int) Math.floor((float) width / (float) reqWidth);
                sampleSize = centerInside
                        ? Math.max(heightRatio, widthRatio)
                        : Math.min(heightRatio, widthRatio);
            }
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        super.onSuccess(bitmap);
        if (null != listener) {
            listener.onSuccess(bitmap);
        }
    }

    @Override
    public void onException(Exception exception) {
        super.onException(exception);
        if (null != listener) {
            listener.onException(exception);
        }
    }

    @Override
    public void onFinally() {
        super.onFinally();
    }


    public interface OnBitmapListener {

        void onSuccess(Bitmap bitmap);

        void onException(Exception exception);
    }

}

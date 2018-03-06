package com.zzzombie.food2work.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageLoader {

    private final Handler handler;

    public ImageLoader(Handler handler) {
        this.handler = handler;
    }

    public void loadImage(ImageView imageView, String urlString) {
        new Thread(() -> {
            InputStream stream = null;
            try {
                URL url = new URL(urlString);
                stream = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                handler.post(() -> imageView.setImageBitmap(bitmap));
            } catch (IOException e) {
                Log.e("ImageLoader", Log.getStackTraceString(e));
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }).start();
    }
}

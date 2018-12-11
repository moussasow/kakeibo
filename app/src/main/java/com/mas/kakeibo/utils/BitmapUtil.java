package com.mas.kakeibo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mas.kakeibo.workers.BitmapWorkerTask;

/**
 * Created by sow.m on 2018/12/11.
 */
public class BitmapUtil {

    public static void loadBitmap(String path, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(path);
    }


    public static Bitmap decodeBitmapFromPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // rotate 90 degrees
        Matrix matrix = new Matrix();
        matrix.postRotate(90.f);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}

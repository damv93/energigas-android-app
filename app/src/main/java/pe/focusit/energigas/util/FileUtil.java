package pe.focusit.energigas.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;

public class FileUtil {

    private static String TAG = FileUtil.class.getSimpleName();

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir    /* directory */
        );
        return image;
    }

    public static void saveJSON(Object object, @Nullable String fileName) {
        Gson gson = new Gson();
        String text = gson.toJson(object);
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myDir = new File(file, "Energigas");
        myDir.mkdirs();
        if (fileName != null) {
            file = new File(myDir, getCode() + "-" + fileName + ".txt");
        } else {
            file = new File(myDir, getCode() + "-" + object.getClass().getSimpleName() + ".txt");
        }
        if (! file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
        saveFile(file, text.getBytes());
    }

    public static void saveFile(File file, byte[] data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(new Date());
    }

    public static Bitmap getScaledBitmap(Bitmap source, int sizeToScale) {
        boolean isWidthGreaterThanHeight = (source.getWidth() > source.getHeight());
        int maxSize = isWidthGreaterThanHeight ? source.getWidth() : source.getHeight();
        if (maxSize > sizeToScale) {
            // Reduce (scale) bitmap size to "sizeToScale"
            float scale = (float) sizeToScale / maxSize;
            float scaledWidth = isWidthGreaterThanHeight ? sizeToScale : scale * source.getWidth();
            float scaledHeight = isWidthGreaterThanHeight ? scale * source.getHeight() : sizeToScale;
            return Bitmap.createScaledBitmap(source, (int)scaledWidth, (int)scaledHeight, false);
        }
        return source;
    }

}

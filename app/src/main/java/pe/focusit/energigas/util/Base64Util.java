package pe.focusit.energigas.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Util {

    public static String encodeFileToBase64(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String encodeImageToBase64(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        Bitmap sourceBitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        Bitmap scaledBitmap = FileUtil.getScaledBitmap(sourceBitmap, 1080);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesToEncode = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytesToEncode, Base64.DEFAULT);
    }

    private static byte[] loadFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();
        /* You cannot create an array using a long type.
            It needs to be an int type.
            Before converting to an int type, check
            to ensure that file is not larger than Integer.MAX_VALUE. */
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        try(InputStream is = new FileInputStream(file)) {
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        return bytes;
    }

}

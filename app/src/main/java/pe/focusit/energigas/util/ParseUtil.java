package pe.focusit.energigas.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

//import kotlin.text.Charsets;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class ParseUtil {

    private static String TAG = ParseUtil.class.getSimpleName();

    public static void parseObject(Object source, Object target) {

        try {
            for (Field sourceField : source.getClass().getDeclaredFields()) {
                sourceField.setAccessible(true);
                if (sourceField.get(source) != null) {
                    try {
                        Field targetField = target.getClass()
                                .getDeclaredField(sourceField.getName());
                        targetField.setAccessible(true);

                        Object sourceData = sourceField.get(source);
                        if (!targetField.getType().equals(sourceField.getType())) {
                            if (targetField.getType().equals(String.class)) {
                                sourceData = String.valueOf(sourceData);
                            } else if (targetField.getType().equals(int.class) || targetField.getType().equals(Integer.class)) {
                                sourceData = Integer.parseInt(sourceData.toString());
                            } else if (targetField.getType().equals(double.class) || targetField.getType().equals(Double.class)) {
                                sourceData = Double.parseDouble(sourceData.toString());
                            } else if (targetField.getType().equals(boolean.class) || targetField.getType().equals(Boolean.class)) {
                                sourceData = Boolean.parseBoolean(sourceData.toString());
                            }
                        }
                        targetField.set(target, sourceData);
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }

    }

    public static String requestBodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String responseBodyToString(final ResponseBody responseBody) {
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            return buffer.clone().readString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

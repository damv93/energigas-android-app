package pe.focusit.energigas.data.net;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pe.focusit.energigas.BuildConfig;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.ParseUtil;
import pe.focusit.energigas.util.SharedPrefs;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestBase {

    private Context mContext;
    private RestApi mRestApi;
    private static String mBaseUrl = BuildConfig.DEBUG ?
            "http://beta.focusit.pe/energigas/" :
            "https://energigas.focusit.pe/";
    private static String mApiUrl = mBaseUrl + "movil-api/";

    public static String LEGAL_TERMS_URL = "http://energigas.focusit.pe/terms.html";

    public RestBase(Context context) {
        this.mContext = context;
        this.mRestApi = createRestApi();
    }

    public RestApi getRestApi() {
        return mRestApi;
    }

    private RestApi createRestApi() {
        if (mRestApi == null) {

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            String userToken = new SharedPrefs(mContext).get(Constants.SharedKey.USER_TOKEN, "");
            if (!TextUtils.isEmpty(userToken)) {
                /* Add interceptor to send http authorization header */
                Interceptor authHeaderInterceptor = chain -> {

                    Request originalRequest = chain.request();
                    String originalRequestBodyStr = ParseUtil.requestBodyToString(originalRequest.body());
                    RequestBody requestBodyCopy = null;
                    if (originalRequest.body() != null) {
                        MediaType contentType = originalRequest.body().contentType();
                        requestBodyCopy = RequestBody.create(contentType, originalRequestBodyStr);
                    }

                    Request newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer " + userToken)
                            .method(originalRequest.method(), requestBodyCopy)
                            .build();

                    Response response = chain.proceed(newRequest);
                    if (response.code() == 200) {
                        MediaType contentType = response.body().contentType();
                        ResponseBody responseBody = ResponseBody.create(contentType, ParseUtil.responseBodyToString(response.body()));
                        return response.newBuilder().body(responseBody).build();
                    }
                    return response;

                };

                okHttpBuilder.addInterceptor(authHeaderInterceptor);
            }

            String baseUrl = mApiUrl;
            if (BuildConfig.DEBUG) {
                /* Add logging interceptor */
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(loggingInterceptor);
            } else {
                // Load CAs from an InputStream
                InputStream caInput = null;
                try {
                    caInput = new BufferedInputStream(mContext.getAssets().open("cert.pem"));
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    Certificate ca = cf.generateCertificate(caInput);
                    System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

                    // Create a KeyStore containing our trusted CAs
                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);

                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);

                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);

                    if (tmf.getTrustManagers().length > 0 && tmf.getTrustManagers()[0] instanceof X509TrustManager) {
                        okHttpBuilder.sslSocketFactory(context.getSocketFactory(), (X509TrustManager) (tmf.getTrustManagers()[0]));
                    }

                } catch (CertificateException | IOException | KeyStoreException |
                        NoSuchAlgorithmException | KeyManagementException e) {
                    baseUrl = "http://energigas.focusit.pe/movil-api/";

                } finally {
                    if (caInput != null) {
                        try {
                            caInput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            /* Create Rest API */
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mRestApi = retrofit.create(RestApi.class);

        }
        return mRestApi;
    }

}

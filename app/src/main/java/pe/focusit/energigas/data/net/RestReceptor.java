package pe.focusit.energigas.data.net;

import android.content.Context;

import pe.focusit.energigas.R;
import pe.focusit.energigas.data.net.response.ApiBaseResponse;
import pe.focusit.energigas.util.CommonUtil;
import pe.focusit.energigas.util.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestReceptor<T> {

    private static final String TAG = RestReceptor.class.getSimpleName();

    private final Context mContext;

    public RestReceptor(Context context) {
        this.mContext = context;
    }

    public void invoke(Call<T> call, RestCallback<T> restCallback) {

        String apiUrl = call.request().url().toString();

        if (CommonUtil.isOnline(this.mContext)) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    switch (response.code()) {
                        case 200:
                            ApiBaseResponse apiResponse = (ApiBaseResponse) response.body();
                            if (apiResponse.getOk())
                                restCallback.onSuccess(response.body());
                            else {
                                String errorMessage = apiResponse.getMsg();
                                LogUtil.e(TAG, apiUrl + ": " + errorMessage);
                                restCallback.onError(new Exception(errorMessage));
                            }
                            break;
                        default:
                            String errorMessage = mContext.getString(R.string.error_msg_rest_failure);
                            LogUtil.e(TAG, apiUrl + ": " + errorMessage);
                            restCallback.onError(new Exception(errorMessage));
                            break;
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    LogUtil.e(TAG, apiUrl + ": " + t.toString());
                    String errorMessage = mContext.getString(R.string.error_msg_rest_failure);
                    restCallback.onError(new Exception(errorMessage));
                }
            });
        } else {
            String errorMessage = mContext.getString(R.string.error_mg_internet_connection);
            LogUtil.e(TAG, apiUrl + ": " + errorMessage);
            restCallback.onError(new Exception(errorMessage));
        }
    }

}

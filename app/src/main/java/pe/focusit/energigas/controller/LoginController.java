package pe.focusit.energigas.controller;

import android.content.Context;

import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.user.UserDtoEntityMapper;
import pe.focusit.energigas.data.entity.UserEntity;
import pe.focusit.energigas.data.entity.UserEntityDao;
import pe.focusit.energigas.data.net.RestCallback;
import pe.focusit.energigas.data.net.RestReceptor;
import pe.focusit.energigas.data.net.dto.UserDto;
import pe.focusit.energigas.data.net.response.LoginResponse;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.util.SharedPrefs;
import pe.focusit.energigas.view.LoginView;
import retrofit2.Call;

public class LoginController extends Controller {

    private static String TAG = LoginController.class.getSimpleName();

    private LoginView mView;
    private UserEntityDao mUserEntityDao;

    public LoginController(Context context, LoginView view) {
        super(context);
        setView(view);
        mUserEntityDao = mDaoSession.getUserEntityDao();
    }

    public void setView(LoginView view) {
        mView = view;
    }

    public void login(String username, String documentNumber) {

        /* Build a user dto to send to the login service */
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setDocumentNumber(documentNumber);

        /* Call the login service */
        Call<LoginResponse> loginCall = mRestBase.getRestApi().login(user);
        RestReceptor<LoginResponse> loginReceptor = new RestReceptor<>(mContext);

        mView.showLoading();

        loginReceptor.invoke(loginCall, new RestCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                LogUtil.i(TAG, "Login - onSuccess");

                /* Remove value in shared preferences indicating that data needs to be synchronized */
                mSharedPrefs.remove(Constants.SharedKey.SYNC);

                mView.hideLoading();

                UserDto userDto = data.getUser();
                if (userDto != null) {
                    /* Save the user locally */
                    saveUser(userDto);
                    /* Store the user id and token in shared preferences indicating that the user is logged in */
                    SharedPrefs sp = new SharedPrefs(mContext);
                    sp.set(Constants.SharedKey.USER_ID, userDto.getId());
                    sp.set(Constants.SharedKey.USER_TOKEN, userDto.getToken());
                    mView.onLoginSuccess();
                } else {
                    mView.onLoginError(mContext.getString(R.string.error_msg_service_data));
                }

            }

            @Override
            public void onError(Exception exception) {
                LogUtil.i(TAG, "Login - onError: " + exception.getMessage());
                mView.hideLoading();
                mView.onLoginError(exception.getMessage());
            }
        });

    }

    private void saveUser(UserDto userDto) {
        /* Build a user entity from its dto */
        UserEntity userEntity = UserDtoEntityMapper.transform(userDto);
        /* Save user entity */
        mUserEntityDao.insertOrReplaceInTx(userEntity);
    }

}

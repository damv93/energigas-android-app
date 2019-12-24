package pe.focusit.energigas.controller;

import android.content.Context;

import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.view.MainView;

public class MainController extends Controller {

    private MainView mView;

    public MainController(Context context, MainView view) {
        super(context);
        mView =  view;
    }

    public boolean isSynced() {
        return mSharedPrefs.get(Constants.SharedKey.SYNC, false);
    }

    public void logout() {
        mSharedPrefs.remove(Constants.SharedKey.USER_ID);
        mSharedPrefs.remove(Constants.SharedKey.USER_TOKEN);
        mView.onLogoutSuccess();
    }

}

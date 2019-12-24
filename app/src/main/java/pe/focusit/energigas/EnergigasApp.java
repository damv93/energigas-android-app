package pe.focusit.energigas;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import pe.focusit.energigas.data.entity.DaoMaster;
import pe.focusit.energigas.data.entity.DaoSession;

public class EnergigasApp extends Application implements EnergigasAppData {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    public void initGreenDao() {
        boolean isBDEncrypted = getApplicationContext().getResources().getBoolean(R.bool.is_bd_encrypted);
        String bdName = getApplicationContext().getString(R.string.bd_name);
        String bdPassword = getApplicationContext().getString(R.string.bd_password);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, bdName);
        Database db = isBDEncrypted ? helper.getEncryptedWritableDb(bdPassword) : helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    @Override
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}

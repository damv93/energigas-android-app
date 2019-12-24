package pe.focusit.energigas.controller;

import android.content.Context;

import pe.focusit.energigas.EnergigasAppData;
import pe.focusit.energigas.controller.mapper.user.UserModelEntityMapper;
import pe.focusit.energigas.data.entity.DaoSession;
import pe.focusit.energigas.data.entity.UserEntity;
import pe.focusit.energigas.data.entity.UserEntityDao;
import pe.focusit.energigas.data.net.RestBase;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.SharedPrefs;

public abstract class Controller {

    private final UserEntityDao mUserEntityDao;

    protected final Context mContext;
    protected final DaoSession mDaoSession;
    protected final RestBase mRestBase;
    protected final SharedPrefs mSharedPrefs;
    protected User mUser;

    public Controller(Context context) {
        mContext = context;
        mDaoSession = ((EnergigasAppData) mContext.getApplicationContext()).getDaoSession();
        mRestBase = new RestBase(context);
        mSharedPrefs = new SharedPrefs(context);
        mUserEntityDao = mDaoSession.getUserEntityDao();
        long userId = mSharedPrefs.get(Constants.SharedKey.USER_ID, 0L);
        if (userId != 0L)
            setUser(userId);
    }

    private void setUser(long userId) {
        UserEntity userEntity = mUserEntityDao.queryBuilder()
                .where(UserEntityDao.Properties.Id.eq(userId))
                .unique();
        mUser = UserModelEntityMapper.transform(userEntity);
    }

    public User getUser() {
        return mUser;
    }

}

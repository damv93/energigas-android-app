package pe.focusit.energigas.controller;

import android.content.Context;

import java.util.List;

import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.deposit.DepositModelEntityMapper;
import pe.focusit.energigas.data.entity.DepositEntity;
import pe.focusit.energigas.data.entity.DepositEntityDao;
import pe.focusit.energigas.model.Deposit;
import pe.focusit.energigas.view.DepositsView;

public class DepositsController extends Controller {

    private static String TAG = DepositsController.class.getSimpleName();

    private DepositsView mView;
    private DepositEntityDao mDepositEntityDao;

    public DepositsController(Context context, DepositsView view) {
        super(context);
        mView = view;
        mDepositEntityDao = mDaoSession.getDepositEntityDao();
    }

    public void getDeposits(long routeId) {
        mView.showLoading();
        List<DepositEntity> depositEntityList = mDepositEntityDao.queryBuilder()
                .where(DepositEntityDao.Properties.RouteId.eq(routeId))
                .list();
        if (depositEntityList == null) {
            mView.onGetDepositsError(mContext.getString(R.string.error_msg_local_data));
            return;
        }
        List<Deposit> deposits = DepositModelEntityMapper.transform(depositEntityList);
        mView.onGetDepositsSuccess(deposits);
    }

}

package pe.focusit.energigas.controller;

import android.content.Context;

import java.util.List;

import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.client.ClientDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.expenseType.ExpenseTypeDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.gasStation.GasStationDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.supplier.SupplierDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.supplierXexpenseType.SupplierxExpenseTypeDtoEntityMapper;
import pe.focusit.energigas.data.entity.ClientEntity;
import pe.focusit.energigas.data.entity.ClientEntityDao;
import pe.focusit.energigas.data.entity.ExpenseTypeEntity;
import pe.focusit.energigas.data.entity.ExpenseTypeEntityDao;
import pe.focusit.energigas.data.entity.GasStationEntity;
import pe.focusit.energigas.data.entity.GasStationEntityDao;
import pe.focusit.energigas.data.entity.SupplierEntity;
import pe.focusit.energigas.data.entity.SupplierEntityDao;
import pe.focusit.energigas.data.entity.SupplierxExpenseTypeEntity;
import pe.focusit.energigas.data.entity.SupplierxExpenseTypeEntityDao;
import pe.focusit.energigas.data.net.RestCallback;
import pe.focusit.energigas.data.net.RestReceptor;
import pe.focusit.energigas.data.net.dto.ClientDto;
import pe.focusit.energigas.data.net.dto.ExpenseTypeDto;
import pe.focusit.energigas.data.net.dto.GasStationDto;
import pe.focusit.energigas.data.net.dto.SupplierDto;
import pe.focusit.energigas.data.net.dto.SupplierxExpenseTypeDto;
import pe.focusit.energigas.data.net.response.SyncResponse;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.view.SyncView;
import retrofit2.Call;

public class SyncController extends Controller {

    private static String TAG = SyncController.class.getSimpleName();

    private SyncView mView;
    private ExpenseTypeEntityDao mExpenseTypeEntityDao;
    private ClientEntityDao mClientEntityDao;
    private SupplierEntityDao mSupplierEntityDao;
    private SupplierxExpenseTypeEntityDao mSupplierxExpenseTypeEntityDao;
    private GasStationEntityDao mGasStationEntityDao;

    public SyncController(Context context, SyncView view) {
        super(context);
        setView(view);
        mExpenseTypeEntityDao = mDaoSession.getExpenseTypeEntityDao();
        mClientEntityDao = mDaoSession.getClientEntityDao();
        mSupplierEntityDao = mDaoSession.getSupplierEntityDao();
        mSupplierxExpenseTypeEntityDao = mDaoSession.getSupplierxExpenseTypeEntityDao();
        mGasStationEntityDao = mDaoSession.getGasStationEntityDao();
    }

    public void setView(SyncView view) {
        mView = view;
    }

    public void sync() {

        /* Call sync service */
        Call<SyncResponse> syncCall = mRestBase.getRestApi().sync();
        RestReceptor<SyncResponse> receptor = new RestReceptor<>(mContext);

        mView.showLoading();

        receptor.invoke(syncCall, new RestCallback<SyncResponse>() {
            @Override
            public void onSuccess(SyncResponse data) {
                LogUtil.i(TAG, "Sync - onSuccess");
                saveSyncData(data);
                /* Store value in shared preferences indicating that data is synchronized */
                mSharedPrefs.set(Constants.SharedKey.SYNC, true);
                mView.hideLoading();
                mView.onSyncSuccess();
            }

            @Override
            public void onError(Exception exception) {
                LogUtil.i(TAG, "Sync - onError: " + exception.getMessage());
                mView.hideLoading();
                mView.onSyncError(exception.getMessage());
            }
        });

    }

    private void saveSyncData(SyncResponse data) {
        mExpenseTypeEntityDao.deleteAll();
        List<ExpenseTypeDto> expenseTypeDtoList = data.getExpenseTypes();
        if (expenseTypeDtoList != null) {
            List<ExpenseTypeEntity> expenseTypeEntityList = ExpenseTypeDtoEntityMapper.transform(expenseTypeDtoList);
            mExpenseTypeEntityDao.insertInTx(expenseTypeEntityList);
        }
        mClientEntityDao.deleteAll();
        List<ClientDto> clientDtoList = data.getClients();
        if (clientDtoList != null) {
            List<ClientEntity> clientEntityList = ClientDtoEntityMapper.transform(clientDtoList);
            mClientEntityDao.insertInTx(clientEntityList);
        }
        mSupplierEntityDao.deleteAll();
        List<SupplierDto> supplierDtoList = data.getSuppliers();
        if (supplierDtoList != null) {
            List<SupplierEntity> supplierEntityList = SupplierDtoEntityMapper.transform(supplierDtoList);
            mSupplierEntityDao.insertInTx(supplierEntityList);
        }
        mSupplierxExpenseTypeEntityDao.deleteAll();
        List<SupplierxExpenseTypeDto> supplierxExpenseTypeDtoList = data.getSupplierxExpenseTypes();
        if (supplierxExpenseTypeDtoList != null) {
            List<SupplierxExpenseTypeEntity> supplierxExpenseTypeEntityList = SupplierxExpenseTypeDtoEntityMapper.transform(supplierxExpenseTypeDtoList);
            mSupplierxExpenseTypeEntityDao.insertInTx(supplierxExpenseTypeEntityList);
        }
        mGasStationEntityDao.deleteAll();
        List<GasStationDto> gasStationDtoList = data.getGasStations();
        if (gasStationDtoList != null) {
            List<GasStationEntity> gasStationEntityList = GasStationDtoEntityMapper.transform(gasStationDtoList);
            mGasStationEntityDao.insertInTx(gasStationEntityList);
        }
    }

}

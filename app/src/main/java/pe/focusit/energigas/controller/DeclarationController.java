package pe.focusit.energigas.controller;

import android.content.Context;

import java.util.List;

import pe.focusit.energigas.controller.mapper.declaration.DeclarationModelDtoMapper;
import pe.focusit.energigas.data.net.RestCallback;
import pe.focusit.energigas.data.net.RestReceptor;
import pe.focusit.energigas.data.net.response.GetDeclarationsResponse;
import pe.focusit.energigas.model.Declaration;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.view.DeclarationsView;
import retrofit2.Call;

public class DeclarationController extends Controller {

    private static String TAG = DeclarationController.class.getSimpleName();

    private DeclarationsView mView;

    public DeclarationController(Context context, DeclarationsView view) {
        super(context);
        mView = view;
    }

    public void getDeclarations() {

        /* Call get-declarations service */
        Call<GetDeclarationsResponse> getDeclarationsCall = mRestBase.getRestApi().getDeclarations();
        RestReceptor<GetDeclarationsResponse> receptor = new RestReceptor<>(mContext);

        mView.showLoading();

        receptor.invoke(getDeclarationsCall, new RestCallback<GetDeclarationsResponse>() {
            @Override
            public void onSuccess(GetDeclarationsResponse data) {
                LogUtil.i(TAG, "GetDeclarations - onSuccess");
                /* Get declarations model from the dto */
                List<Declaration> pending = DeclarationModelDtoMapper.transform(data.getPending());
                List<Declaration> observed = DeclarationModelDtoMapper.transform(data.getObserved());
                List<Declaration> approved = DeclarationModelDtoMapper.transform(data.getApproved());
                mView.hideLoading();
                mView.onGetDeclarationsSuccess(pending, observed, approved);
            }

            @Override
            public void onError(Exception exception) {
                LogUtil.i(TAG, "GetDeclarations - onError: " + exception.getMessage());
                mView.hideLoading();
                mView.onGetDeclarationsError(exception.getMessage());
            }
        });

    }

}

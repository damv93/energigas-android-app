package pe.focusit.energigas.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.DepositsController;
import pe.focusit.energigas.model.Deposit;
import pe.focusit.energigas.view.DepositsView;
import pe.focusit.energigas.view.adapter.DepositsAdapter;

public class DepositsFragment extends BaseFragment implements DepositsView {

    private static final String ROUTE_ID_PARAM = "route_id_param";

    @BindView(R.id.rv_deposits)
    RecyclerView rvDeposits;

    private long mRouteId;
    private DepositsController mController;

    public DepositsFragment() {
        // Required empty public constructor
    }

    public static DepositsFragment newInstance(long routeId) {
        DepositsFragment fragment = new DepositsFragment();
        Bundle args = new Bundle();
        args.putLong(ROUTE_ID_PARAM, routeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRouteId = getArguments().getLong(ROUTE_ID_PARAM, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deposits, container, false);
        ButterKnife.bind(this, view);

        rvDeposits.setLayoutManager(new LinearLayoutManager(getContext()));

        mController = new DepositsController(getContext(), this);
        mController.getDeposits(mRouteId);

        return view;
    }

    @Override
    public void onGetDepositsSuccess(List<Deposit> deposits) {
        DepositsAdapter adapter = new DepositsAdapter();
        adapter.setData(deposits);
        rvDeposits.setAdapter(adapter);
    }

    @Override
    public void onGetDepositsError(String errorMessage) {
        showMessage(getString(R.string.lbl_error), errorMessage);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}

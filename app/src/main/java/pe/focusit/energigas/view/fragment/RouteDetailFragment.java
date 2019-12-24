package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.RouteDetailController;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.view.RouteDetailView;
import pe.focusit.energigas.view.adapter.RouteDetailAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RouteDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RouteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteDetailFragment extends BaseFragment implements
        RouteDetailView, RouteDetailAdapter.OnGoToSegmentDetailListener {

    private OnFragmentInteractionListener mListener;

    private RouteDetailController mController;
    private RouteDetailAdapter mAdapter;
    private Route mMyRoute;
    private MaterialDialog mDialog;

    @BindView(R.id.crl_main)
    View crlMain;
    @BindView(R.id.tv_indications)
    TextView tvIndications;
    @BindView(R.id.rv_route_detail)
    RecyclerView rvRouteDetail;
    @BindView(R.id.btn_send_for_revision)
    Button btnSendForRevision;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.crl_send_declaration_success)
    View crlSendDeclarationSuccess;
    @BindView(R.id.tv_congrats)
    TextView tvCongrats;

    public RouteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RouteDetailFragment.
     */
    public static RouteDetailFragment newInstance() {
        RouteDetailFragment fragment = new RouteDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route_detail, container, false);
        ButterKnife.bind(this, view);
        rvRouteDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RouteDetailAdapter(this);
        rvRouteDetail.setAdapter(mAdapter);
        mController = new RouteDetailController(getContext(), this);
        User user = mController.getUser();
        tvIndications.setText(getString(R.string.txt_declare_expenses_product_dispatch, user.getName()));
        setActionBarView();
        mController.getMyRoute();
        return view;
    }

    @OnClick(R.id.img_back)
    void onGoBack() {
        if (mListener != null)
            mListener.loadFragment(MyRouteFragment.newInstance());
    }

    public boolean onBackPressed() {
        if (mListener != null) {
            mListener.loadFragment(MyRouteFragment.newInstance());
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void setActionBarView() {
        View view = getLayoutInflater()
                .inflate(R.layout.toolbar_driver_info, mListener.getAppActionBar(), false);
        User user = mController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        mListener.setActionBarView(view);
    }

    private void setActionBarViewWithBudgetBalance(double routeBudgetBalance) {
        View view = getLayoutInflater()
                .inflate(R.layout.toolbar_driver_balance_info, mListener.getAppActionBar(), false);
        User user = mController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        TextView tvBudgetBalance = view.findViewById(R.id.tv_budget_balance_value);
        tvBudgetBalance.setText(getString(R.string.lbl_expense_amount_number, routeBudgetBalance));
        mListener.setActionBarView(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
        mListener = null;
    }

    @Override
    public void onGetMyRouteSuccess(@NonNull Route route) {
        if (!isAdded())
            return;
        mMyRoute = route;
        /* Set the action bar info with the driver name and the route budget balance */
        setActionBarViewWithBudgetBalance(mMyRoute.getBudgetBalance());
        /* Show the route segments info */
        mAdapter.setData(mMyRoute.getRouteSegments());
        /* Change indications text and show "Send for revision" button if all route segments are closed */
        if (route.getAreAllDispatchesClosed()) {
            tvIndications.setText(getString(R.string.txt_all_routes_completed, mController.getUser().getName()));
            btnSendForRevision.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetMyRouteError(String errorMessage) {

    }

    @Override
    public void onGoToSegmentDetail(RouteSegment routeSegment) {
        mListener.loadFragment(SegmentDetailFragment.newInstance(mMyRoute, routeSegment));
    }

    @OnClick(R.id.btn_send_for_revision)
    void sendForRevision() {
        View confirmationView = getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
        Button negativeButton = confirmationView.findViewById(R.id.btn_negative);
        negativeButton.setOnClickListener(view -> {
            if (mDialog != null)
                mDialog.dismiss();
        });
        Button positiveButton = confirmationView.findViewById(R.id.btn_positive);
        positiveButton.setOnClickListener(view -> {
            btnSendForRevision.setEnabled(false);
            if (mDialog != null)
                mDialog.dismiss();
            mController.sendDeclaration();
        });
        mDialog = new MaterialDialog.Builder(getContext())
                .customView(confirmationView, false)
                .show();
    }

    @Override
    public void onSendDeclarationSuccess() {
        if (!isAdded())
            return;
        /* Update action bar (don't show the budget balance) */
        setActionBarView();
        /* Show success view */
        tvCongrats.setText(getString(R.string.lbl_send_declaration_congrats, mController.getUser().getName()));
        crlSendDeclarationSuccess.setVisibility(View.VISIBLE);
        crlMain.setVisibility(View.GONE);
    }

    @Override
    public void onSendDeclarationError(String errorMessage) {
        if (!isAdded())
            return;
        showMessage(getString(R.string.lbl_error), errorMessage);
        btnSendForRevision.setEnabled(true);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void loadFragment(BaseFragment fragment);
        Toolbar getAppActionBar();
        void setActionBarView(View view);
    }
}

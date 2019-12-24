package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.MyRouteController;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.model.Vehicle;
import pe.focusit.energigas.view.MyRouteView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyRouteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyRouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRouteFragment extends BaseFragment implements MyRouteView {

    @BindView(R.id.crl_my_route)
    ConstraintLayout crlMyRoute;
    @BindView(R.id.tv_route_points)
    TextView tvRoutePoints;
    @BindView(R.id.tv_fleet_id)
    TextView tvFleetId;
    @BindView(R.id.tv_fuel)
    TextView tvFuel;
    @BindView(R.id.btn_start_route)
    FrameLayout btnStartRoute;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.view_no_route_assigned)
    View viewNoRouteAssigned;
    @BindView(R.id.tv_greeting)
    TextView tvGreeting;
    @BindView(R.id.tv_view_deposits)
    TextView tvViewDeposits;

    private MyRouteController mMyRouteController;
    private Route mMyRoute;

    private OnFragmentInteractionListener mListener;

    public MyRouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyRouteFragment.
     */
    public static MyRouteFragment newInstance() {
        MyRouteFragment fragment = new MyRouteFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_route, container, false);
        ButterKnife.bind(this, view);
        mMyRouteController = new MyRouteController(getContext(), this);
        setActionBarView();
        btnStartRoute.setEnabled(false);
        mMyRouteController.getMyRoute();
        return view;
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
        User user = mMyRouteController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        mListener.setActionBarView(view);
    }

    private void setActionBarViewWithBudgetBalance(double routeBudgetBalance) {
        View view = getLayoutInflater()
                .inflate(R.layout.toolbar_driver_balance_info, mListener.getAppActionBar(), false);
        User user = mMyRouteController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        TextView tvBudgetBalance = view.findViewById(R.id.tv_budget_balance_value);
        tvBudgetBalance.setText(getString(R.string.lbl_expense_amount_number, routeBudgetBalance));
        mListener.setActionBarView(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMyRouteController = null;
        mListener = null;
    }

    @Override
    public void onGetMyRouteSuccess(@NonNull Route route) {
        if (!isAdded())
            return;
        mMyRoute = route;
        /* Set the action bar info with the driver name and the route budget balance */
        setActionBarViewWithBudgetBalance(route.getBudgetBalance());
        /* Show my route info */
        tvRoutePoints.setText(route.getName());
        Vehicle vehicle = route.getVehicle();
        if (vehicle != null)
            tvFleetId.setText(vehicle.getUnitTract());
        else
            tvFleetId.setText(R.string.msg_driver_not_have_vehicle);
        tvFuel.setText(route.getFuelToTransport());
        /* Enable "Start route" button */
        btnStartRoute.setEnabled(true);
        /* Show "View deposits" text */
        //tvViewDeposits.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetMyRouteError(String errorMessage) {
        if (!isAdded())
            return;
        /* Disable "Start route" button and show error message */
        btnStartRoute.setEnabled(false);
        showMessage(getString(R.string.lbl_error), errorMessage);
    }

    @OnClick(R.id.btn_start_route)
    public void startRoute() {
        /* Set route departure date */
        mMyRouteController.startRoute();
        /* Go to route detail */
        mListener.loadFragment(RouteDetailFragment.newInstance());
    }

    @Override
    public void onNoRouteAssigned() {
        if (!isAdded())
            return;
        crlMyRoute.setVisibility(View.GONE);
        tvGreeting.setText(getString(R.string.txt_user_greeting, mMyRouteController.getUser().getName()));
        viewNoRouteAssigned.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_view_deposits)
    void viewDeposits() {
        mListener.loadFragment(DepositsFragment.newInstance(mMyRoute.getId()));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        btnStartRoute.setEnabled(true);
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

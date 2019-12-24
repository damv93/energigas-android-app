package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.focusit.energigas.BuildConfig;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.MyProfileController;
import pe.focusit.energigas.model.Vehicle;
import pe.focusit.energigas.view.MyProfileView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends BaseFragment implements MyProfileView {

    private OnFragmentInteractionListener mListener;

    private MyProfileController mController;

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;
    @BindView(R.id.tv_doc_number)
    TextView tvDocNumber;
    @BindView(R.id.tv_plate_number)
    TextView tvPlateNumber;
    @BindView(R.id.tv_available_value)
    TextView tvAvailable;
    @BindView(R.id.tv_db_backup)
    TextView tvBdBackup;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyProfileFragment.
     */
    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        //tvBdBackup.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
        mController = new MyProfileController(getContext(), this);
        setActionBarView();
        /* Set user info */
        tvDriverName.setText(mController.getUser().getFullName());
        tvDocNumber.setText(mController.getUser().getDocumentNumber());
        /* Get vehicle info */
        mController.getVehicleAssigned();
        /* Get user budget balance */
        mController.getBudgetBalance();
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
                .inflate(R.layout.toolbar_back_info, mListener.getAppActionBar(), false);
        TextView infoText = view.findViewById(R.id.tv_action_bar_info);
        infoText.setText(R.string.lbl_profile);
        ImageView imgBack = view.findViewById(R.id.img_back_arrow);
        imgBack.setOnClickListener(v -> onBackPressed());
        mListener.setActionBarView(view);
    }

    @Override
    public void onGetVehicleSuccess(@NonNull Vehicle vehicle) {
        tvPlateNumber.setText(vehicle.getUnitTract());
    }

    @Override
    public void onGetVehicleError(String errorMessage) {
        tvPlateNumber.setText(errorMessage);
    }

    @Override
    public void onGetBudgetBalanceSuccess(double budgetBalance) {
        tvAvailable.setText(getString(R.string.lbl_expense_amount_number, budgetBalance));
    }

    @Override
    public void onGetBudgetBalanceError(String errorMessage) {
        showMessage(getString(R.string.lbl_error), errorMessage);
    }

    @Override
    public boolean onBackPressed() {
        if (mListener != null) {
            mListener.loadFragment(MyRouteFragment.newInstance());
            return true;
        }
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.tv_logout)
    void logout() {
        mListener.logout();
    }

    @OnClick(R.id.tv_db_backup)
    void generateBdBackup() {
        mListener.generateDbBackup();
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
        void logout();
        void generateDbBackup();
    }
}

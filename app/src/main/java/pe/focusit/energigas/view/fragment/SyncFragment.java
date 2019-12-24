package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.SyncController;
import pe.focusit.energigas.util.CommonUtil;
import pe.focusit.energigas.view.SyncView;

public class SyncFragment extends BaseFragment implements SyncView {

    private OnFragmentInteractionListener mListener;
    private SyncController mController;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_error_msg)
    TextView tvErrorMsg;
    @BindView(R.id.tv_error_sub_msg)
    TextView tvErrorSubMsg;
    @BindView(R.id.tv_retry)
    TextView tvRetry;
    @BindView(R.id.img_offline)
    ImageView imgOffline;

    public SyncFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SyncFragment.
     */
    public static SyncFragment newInstance() {
        SyncFragment fragment = new SyncFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sync, container, false);
        ButterKnife.bind(this, view);

        mController = new SyncController(getContext(), this);

        if (!CommonUtil.isOnline(getContext())) {
            progressBar.setVisibility(View.GONE);
            tvErrorMsg.setText(R.string.msg_connect_to_internet);
            tvErrorSubMsg.setText(R.string.msg_check_connection);
            imgOffline.setVisibility(View.VISIBLE);
            tvErrorMsg.setVisibility(View.VISIBLE);
            tvErrorSubMsg.setVisibility(View.VISIBLE);
            tvRetry.setVisibility(View.VISIBLE);
        } else {
            mController.sync();
        }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mController = null;
    }

    @Override
    public void onSyncSuccess() {
        mListener.onSyncSuccess();
    }

    @Override
    public void onSyncError(String errorMessage) {
        tvErrorMsg.setText(R.string.error_msg_rest_failure);
        tvErrorSubMsg.setText(errorMessage);
        imgOffline.setVisibility(View.VISIBLE);
        tvErrorMsg.setVisibility(View.VISIBLE);
        tvErrorSubMsg.setVisibility(View.VISIBLE);
        tvRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        imgOffline.setVisibility(View.GONE);
        tvErrorMsg.setVisibility(View.GONE);
        tvErrorSubMsg.setVisibility(View.GONE);
        tvRetry.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_retry)
    void retry() {
        if (!CommonUtil.isOnline(getContext())) {
            progressBar.setVisibility(View.GONE);
            tvErrorMsg.setText(R.string.msg_connect_to_internet);
            tvErrorSubMsg.setText(R.string.msg_check_connection);
            imgOffline.setVisibility(View.VISIBLE);
            tvErrorMsg.setVisibility(View.VISIBLE);
            tvErrorSubMsg.setVisibility(View.VISIBLE);
            tvRetry.setVisibility(View.VISIBLE);
        } else {
            mController.sync();
        }
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
        void onSyncSuccess();
    }

}

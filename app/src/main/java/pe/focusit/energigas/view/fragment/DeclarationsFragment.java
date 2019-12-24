package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.DeclarationController;
import pe.focusit.energigas.model.Declaration;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.view.DeclarationsView;
import pe.focusit.energigas.view.adapter.DeclarationsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeclarationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeclarationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeclarationsFragment extends BaseFragment implements DeclarationsView {

    private OnFragmentInteractionListener mListener;

    private DeclarationController mController;

    @BindView(R.id.tbl_declarations)
    TabLayout tblDeclarations;
    @BindView(R.id.vp_declarations)
    ViewPager vpDeclarations;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public DeclarationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DeclarationsFragment.
     */
    public static DeclarationsFragment newInstance() {
        return new DeclarationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_declarations, container, false);
        ButterKnife.bind(this, view);

        mController = new DeclarationController(getContext(), this);

        setActionBarView();

        mController.getDeclarations();

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
        User user = mController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        mListener.setActionBarView(view);
    }

    @Override
    public void onGetDeclarationsSuccess(List<Declaration> pending, List<Declaration> observed, List<Declaration> approved) {
        if (!isAdded())
            return;
        DeclarationsPagerAdapter pagerAdapter = new DeclarationsPagerAdapter(getContext(), getChildFragmentManager());
        pagerAdapter.addFragment(DeclarationStatusFragment
                .newInstance(DeclarationStatusFragment.STATUS_PENDING, pending));
        pagerAdapter.addFragment(DeclarationStatusFragment
                .newInstance(DeclarationStatusFragment.STATUS_OBSERVED, observed));
        pagerAdapter.addFragment(DeclarationStatusFragment
                .newInstance(DeclarationStatusFragment.STATUS_APPROVED, approved));
        vpDeclarations.setAdapter(pagerAdapter);
        tblDeclarations.setupWithViewPager(vpDeclarations);
    }

    @Override
    public void onGetDeclarationsError(String errorMessage) {
        if (!isAdded())
            return;
        showMessage(getString(R.string.lbl_error), errorMessage);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mController = null;
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
        Toolbar getAppActionBar();
        void setActionBarView(View view);
    }
}

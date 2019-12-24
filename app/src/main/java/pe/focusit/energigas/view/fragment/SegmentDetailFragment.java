package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.SegmentDetailController;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.view.adapter.DeclarationsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SegmentDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SegmentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SegmentDetailFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    private SegmentDetailController mController;
    private Route mMyRoute;
    private RouteSegment mRouteSegment;

    @BindView(R.id.tbl_declarations)
    TabLayout tblDeclarations;
    @BindView(R.id.vp_declarations)
    ViewPager vpDeclarations;

    public SegmentDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SegmentDetailFragment.
     */
    public static SegmentDetailFragment newInstance(Route route, RouteSegment routeSegment) {
        SegmentDetailFragment fragment = new SegmentDetailFragment();
        fragment.setRoute(route);
        fragment.setRouteSegment(routeSegment);
        return fragment;
    }

    private void setRoute(Route route) {
        mMyRoute = route;
    }

    private void setRouteSegment(RouteSegment routeSegment) {
        mRouteSegment = routeSegment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_segment_detail, container, false);
        ButterKnife.bind(this, view);
        setActionBarView(mMyRoute.getBudgetBalance());
        DeclarationsPagerAdapter pagerAdapter = new DeclarationsPagerAdapter(getContext(), getChildFragmentManager());
        pagerAdapter.addFragment(ExpenseDetailFragment.newInstance(mMyRoute, mRouteSegment));
        pagerAdapter.addFragment(ProductDispatchFragment.newInstance(mRouteSegment));
        vpDeclarations.setAdapter(pagerAdapter);
        tblDeclarations.setupWithViewPager(vpDeclarations);
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

    private void setActionBarView(double routeBudgetBalance) {
        View view = getLayoutInflater()
                .inflate(R.layout.toolbar_back_balance_info, mListener.getAppActionBar(), false);
        TextView infoText = view.findViewById(R.id.tv_action_bar_info);
        infoText.setText(mRouteSegment.getSegmentDescription());
        ImageView imgBack = view.findViewById(R.id.img_back_arrow);
        imgBack.setOnClickListener(v -> onBackPressed());
        TextView tvBudgetBalance = view.findViewById(R.id.tv_budget_balance_value);
        tvBudgetBalance.setText(getString(R.string.lbl_expense_amount_number, routeBudgetBalance));
        mListener.setActionBarView(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mController = null;
    }

    @Override
    public boolean onBackPressed() {
        if (mListener != null) {
            mListener.loadFragment(RouteDetailFragment.newInstance());
            return true;
        }
        return false;
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

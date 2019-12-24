package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.model.Declaration;
import pe.focusit.energigas.view.adapter.DeclarationsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeclarationStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeclarationStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeclarationStatusFragment extends BaseFragment implements DeclarationsAdapter.OnShowObservationListener {

    private static final String STATUS_PARAM = "status_param";
    public static final int STATUS_APPROVED = 0;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_OBSERVED = 2;

    private int mStatus;
    private List<Declaration> mDeclarations;
    private int mTitleStringResource;
    private DeclarationsAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_declaration_status)
    TextView tvDeclarationStatus;
    @BindView(R.id.rv_declarations)
    RecyclerView rvDeclarations;

    public DeclarationStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param declarations Declarations that will to be shown.
     * @return A new instance of fragment DeclarationStatusFragment.
     */
    public static DeclarationStatusFragment newInstance(int status, List<Declaration> declarations) {
        DeclarationStatusFragment fragment = new DeclarationStatusFragment();
        Bundle args = new Bundle();
        args.putInt(STATUS_PARAM, status);
        fragment.setArguments(args);
        fragment.setTitleStringResource(status);
        fragment.mDeclarations = declarations;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatus = getArguments().getInt(STATUS_PARAM, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_declaration_status, container, false);
        ButterKnife.bind(this, view);

        String declarationStatus = "";
        switch (mStatus) {
            case STATUS_APPROVED:
                declarationStatus = getString(R.string.lbl_approved_declarations);
                break;
            case STATUS_PENDING:
                declarationStatus = getString(R.string.lbl_pending_declarations);
                break;
            case STATUS_OBSERVED:
                declarationStatus = getString(R.string.lbl_observed_declarations);
                break;
        }
        tvDeclarationStatus.setText(declarationStatus);

        rvDeclarations.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DeclarationsAdapter(mStatus, this);
        mAdapter.setData(mDeclarations);
        rvDeclarations.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onShowObservation(Declaration observedDeclaration) {
        showMessage(getString(R.string.lbl_declaration_observation), observedDeclaration.getObservation());
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
    }

    private void setTitleStringResource(int status) {
        switch (status) {
            case STATUS_APPROVED:
                mTitleStringResource = R.string.lbl_approved_status;
                return;
            case STATUS_PENDING:
                mTitleStringResource = R.string.lbl_pending_status;
                return;
            case STATUS_OBSERVED:
                mTitleStringResource = R.string.lbl_observed_status;
                return;
            default:
                mTitleStringResource = R.string.lbl_unknown_status;
                return;
        }
    }

    @Override
    public int getTitleStringResource() {
        return mTitleStringResource;
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

    }
}

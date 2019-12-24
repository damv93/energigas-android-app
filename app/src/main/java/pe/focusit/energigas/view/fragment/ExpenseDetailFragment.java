package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.ExpenseDetailController;
import pe.focusit.energigas.model.Expense;
import pe.focusit.energigas.model.ExpenseType;
import pe.focusit.energigas.model.GasStation;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.model.Supplier;
import pe.focusit.energigas.util.CommonUtil;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.DateUtil;
import pe.focusit.energigas.util.FileUtil;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.util.ParseUtil;
import pe.focusit.energigas.view.ExpenseDetailView;
import pe.focusit.energigas.view.adapter.ExpensesAdapter;
import pe.focusit.energigas.view.util.ImageOverlayView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenseDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseDetailFragment extends BaseFragment implements
        ExpenseDetailView, ExpensesAdapter.OnExpenseInteractionListener {

    private static String TAG = ExpenseDetailFragment.class.getSimpleName();
    private static int REQUEST_IMAGE_CAPTURE = 1;

    private OnFragmentInteractionListener mListener;

    private ExpenseDetailController mController;
    private ExpensesAdapter mExpensesAdapter;
    private Route mMyRoute;
    private RouteSegment mRouteSegment;
    private Expense mExpense; // Represents the Expense being added or edited
    private Expense mExpenseRegistered; // Represents the Expense registered in local BD
    private String mPreviousPhotoFilePath;
    private boolean mSkipOnRucTextChanged = false;
    private ImageOverlayView mImageOverlayView;

    @BindView(R.id.ll_expenses)
    LinearLayout llExpenses;
    @BindView(R.id.tv_indications)
    TextView tvIndications;
    @BindView(R.id.rv_expenses)
    RecyclerView rvExpenses;
    @BindView(R.id.cv_expense_detail_form)
    MaterialCardView cvExpenseDetailForm;
    @BindView(R.id.sv_expense_detail_form)
    ScrollView svExpenseForm;
    @BindView(R.id.btn_close_expense_form)
    Button btnCloseExpenseForm;
    @BindView(R.id.cl_expense_detail_form)
    ConstraintLayout clExpenseDetailForm;
    @BindView(R.id.tv_expense_type_selected)
    TextView tvExpenseTypeSelected;
    @BindView(R.id.ll_expense_types)
    LinearLayout llExpenseTypes;
    @BindView(R.id.cv_expense_types)
    MaterialCardView cvExpenseTypes;
    @BindView(R.id.tv_expense_sub_type_selected)
    TextView tvExpenseSubTypeSelected;
    @BindView(R.id.ll_expense_sub_types)
    LinearLayout llExpenseSubTypes;
    @BindView(R.id.cv_expense_sub_types)
    MaterialCardView cvExpenseSubTypes;
    @BindView(R.id.tv_expense_item_date_value)
    TextView tvExpenseDate;
    @BindView(R.id.rdg_fuel_provider)
    RadioGroup rdgFuelProvider;
    @BindView(R.id.rb_energigas)
    RadioButton rbEnergigas;
    @BindView(R.id.rb_external)
    RadioButton rbExternal;
    @BindView(R.id.tv_ruc_number)
    TextView tvRucNumber;
    @BindView(R.id.til_ruc_number)
    TextInputLayout tilExpenseRuc;
    @BindView(R.id.edt_ruc_number)
    AutoCompleteTextView edtExpenseRuc;
    @BindView(R.id.tv_voucher_type)
    TextView tvVoucherType;
    @BindView(R.id.rdg_voucher_type)
    RadioGroup rdgVoucherType;
    @BindView(R.id.rb_invoice)
    RadioButton rbInvoice;
    @BindView(R.id.rb_bill)
    RadioButton rbBill;
    @BindView(R.id.tv_voucher_number)
    TextView tvVoucherNumber;
    @BindView(R.id.til_voucher_number)
    TextInputLayout tilVoucherNumber;
    @BindView(R.id.edt_voucher_number)
    EditText edtVoucherNumber;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.til_amount)
    TextInputLayout tilAmount;
    @BindView(R.id.edt_amount)
    EditText edtExpenseAmount;
    @BindView(R.id.tv_gas_station)
    TextView tvGasStation;
    @BindView(R.id.cv_gas_stations)
    MaterialCardView cvGasStations;
    @BindView(R.id.ll_gas_stations)
    LinearLayout llGasStations;
    @BindView(R.id.tv_gallons)
    TextView tvGallons;
    @BindView(R.id.til_gallons)
    TextInputLayout tilGallons;
    @BindView(R.id.edt_gallons)
    EditText edtGallons;
    @BindView(R.id.tv_mileage)
    TextView tvMileage;
    @BindView(R.id.til_mileage)
    TextInputLayout tilMileage;
    @BindView(R.id.edt_mileage)
    EditText edtMileage;
    @BindView(R.id.img_attach_photo)
    ImageView imgAttachPhoto;
    @BindView(R.id.til_observation)
    TextInputLayout tilObservation;
    @BindView(R.id.edt_observation)
    EditText edtObservation;
    @BindView(R.id.btn_save_expense)
    Button btnSaveExpense;

    private TextWatcher mRucTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().equals(
                    String.format(Locale.getDefault(), "%s - %s", mExpense.getRucNumber(), mExpense.getSupplierName())))
                tilExpenseRuc.setError(null);
            else
                onTextChangedRucNumber(editable);
        }
    };

    public ExpenseDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExpenseDetailFragment.
     */
    public static ExpenseDetailFragment newInstance(@NonNull Route route, @NonNull RouteSegment routeSegment) {
        ExpenseDetailFragment fragment = new ExpenseDetailFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        ButterKnife.bind(this, view);

        cvExpenseDetailForm.setClipToOutline(false); // this is to correctly show the close (x) button

        rvExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        mExpensesAdapter = new ExpensesAdapter(mRouteSegment,this);
        rvExpenses.setAdapter(mExpensesAdapter);

        mController = new ExpenseDetailController(getContext(), this, mMyRoute, mRouteSegment);
        mController.getExpenses();
        mController.getExpenseTypes();
        mController.getGasStations();

        return view;
    }

    @Override
    public void onGetExpensesSuccess(@NonNull List<Expense> expenses) {
        if (!isAdded())
            return;
        tvIndications.setVisibility(expenses.isEmpty() ? View.VISIBLE : View.GONE);
        mExpensesAdapter.setData(expenses);
    }

    @Override
    public void onGetExpensesError() {

    }

    @Override
    public void onGetExpenseTypesSuccess(@NonNull List<ExpenseType> expenseTypes) {
        if (!isAdded())
            return;
        initExpenseTypesListView(expenseTypes);
    }

    @Override
    public void onGetExpenseTypesError() {

    }

    private void initExpenseTypesListView(List<ExpenseType> expenseTypes) {
        for (ExpenseType expenseType : expenseTypes) {
            View expenseTypeView = getLayoutInflater().inflate(R.layout.item_expense_type, llExpenseTypes, false);
            TextView expenseTypeName = expenseTypeView.findViewById(R.id.tv_expense_type_name);
            expenseTypeName.setText(expenseType.getDescription());
            expenseTypeView.setTag(expenseType);
            expenseTypeView.setOnClickListener(view -> {
                ExpenseType expenseTypeSelected = (ExpenseType) view.getTag();
                tvExpenseTypeSelected.setText(expenseTypeSelected.getDescription());
                /* Set expense type icon */
                tvExpenseTypeSelected.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_toll_small, 0, R.drawable.ic_down_arrow, 0);
                if (mExpense != null)
                    mExpense.setExpenseType(expenseTypeSelected);

                /* Fill expense sub types list view */
                fillExpenseSubTypesListView(expenseTypeSelected.getChildren());
                if (mExpense != null)
                    mExpense.setExpenseSubType(null);
                if (expenseTypeSelected.getChildren() != null && !expenseTypeSelected.getChildren().isEmpty()) {
                    tvExpenseSubTypeSelected.setVisibility(View.VISIBLE);
                } else {
                    tvExpenseSubTypeSelected.setVisibility(View.GONE);
                }

                /* Set ruc number (supplier) autocomplete adapter */
                setRucAutocompleteAdapter();
                edtExpenseRuc.setText("");
                if (mExpense != null) {
                    /* Reset supplier related info */
                    mExpense.setSupplierId(null);
                    mExpense.setRucNumber(null);
                    mExpense.setSupplierName(null);
                }

                if (Constants.FUEL_CONSUMPTION_ITEM_ID.equalsIgnoreCase(expenseTypeSelected.getName().trim())) {
                    tvVoucherType.setVisibility(View.GONE);
                    rdgVoucherType.setVisibility(View.GONE);
                    rdgFuelProvider.setVisibility(View.VISIBLE);
                    rdgFuelProvider.check(R.id.rb_energigas);
                    tvGallons.setVisibility(View.VISIBLE);
                    tilGallons.setVisibility(View.VISIBLE);
                    tvMileage.setVisibility(View.VISIBLE);
                    tilMileage.setVisibility(View.VISIBLE);
                } else {
                    tvVoucherType.setVisibility(View.VISIBLE);
                    rdgVoucherType.setVisibility(View.VISIBLE);
                    tvVoucherNumber.setText(R.string.lbl_voucher_number);
                    rdgFuelProvider.setVisibility(View.GONE);
                    rdgFuelProvider.clearCheck();
                    if (mExpense != null)
                        mExpense.setExternalProvider(null);
                    tvGasStation.setVisibility(View.GONE);
                    cvGasStations.setVisibility(View.GONE);
                    tvGasStation.setText(R.string.lbl_select_gas_station);
                    if (mExpense != null)
                        mExpense.setEnergigasStation(null);
                    tvGallons.setVisibility(View.GONE);
                    tilGallons.setVisibility(View.GONE);
                    edtGallons.setText("");
                    tvMileage.setVisibility(View.GONE);
                    tilMileage.setVisibility(View.GONE);
                    edtMileage.setText("");
                }

                if (Constants.PARKING_LOT_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim()) ||
                        Constants.VIATICUM_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) {
                    rdgVoucherType.clearCheck();
                    if (mExpense != null)
                        mExpense.setVoucherType(null);
                } else if (rdgVoucherType.getCheckedRadioButtonId() == -1) {
                    rdgVoucherType.check(R.id.rb_invoice);
                }

                if (expenseTypeSelected.getId() != null)
                    enableViews(clExpenseDetailForm);
                svExpenseForm.scrollTo(0, 0); // Scroll form to top
                btnSaveExpense.setEnabled(true);
                cvExpenseTypes.setVisibility(View.GONE);
            });
            llExpenseTypes.addView(expenseTypeView);
        }
    }

    private void fillExpenseSubTypesListView(List<ExpenseType> expenseTypes) {
        tvExpenseSubTypeSelected.setText(R.string.lbl_select_expense_sub_type);
        llExpenseSubTypes.removeAllViews();
        if (expenseTypes == null)
            return;
        for (ExpenseType expenseType : expenseTypes) {
            View expenseTypeView = getLayoutInflater().inflate(R.layout.item_expense_type, llExpenseSubTypes, false);
            expenseTypeView.findViewById(R.id.img_expense_type).setVisibility(View.GONE);
            TextView expenseTypeName = expenseTypeView.findViewById(R.id.tv_expense_type_name);
            expenseTypeName.setText(expenseType.getDescription());
            expenseTypeView.setTag(expenseType);
            expenseTypeView.setOnClickListener(view -> {
                ExpenseType expenseSubTypeSelected = (ExpenseType) view.getTag();
                tvExpenseSubTypeSelected.setText(expenseSubTypeSelected.getDescription());
                tvExpenseSubTypeSelected.setTextColor(Color.parseColor("#363636"));
                if (mExpense != null)
                    mExpense.setExpenseSubType(expenseSubTypeSelected);
                svExpenseForm.scrollTo(0, 0); // Scroll form to top
                cvExpenseSubTypes.setVisibility(View.GONE);
            });
            llExpenseSubTypes.addView(expenseTypeView);
        }

    }

    private void setRucAutocompleteAdapter() {
        edtExpenseRuc.setAdapter(null);
        edtExpenseRuc.setOnItemSelectedListener(null);
        if (mExpense == null)
            return;
        final List<Supplier> suppliers;
        if (mExpense.getExpenseType() != null && mExpense.getExpenseType().getSuppliers() != null &&
                !mExpense.getExpenseType().getSuppliers().isEmpty()) {
            suppliers = mExpense.getExpenseType().getSuppliers();
        } else {
            return;
        }
        List<String> suppliersAdapter = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            suppliersAdapter.add(String.format(Locale.getDefault(), "%s - %s", supplier.getRuc(), supplier.getName()));
        }
        edtExpenseRuc.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, suppliersAdapter));
        edtExpenseRuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String supplierSelectedStr = (String) adapterView.getItemAtPosition(i);
                int pos = 0;
                for (String supplierStr : suppliersAdapter) {
                    if (supplierStr.equals(supplierSelectedStr)) {
                        Supplier supplierSelected = suppliers.get(pos);
                        mExpense.setSupplierId(supplierSelected.getId());
                        mExpense.setRucNumber(supplierSelected.getRuc());
                        mExpense.setSupplierName(supplierSelected.getName());
                        tilExpenseRuc.setError(null);
                        break;
                    }
                    pos++;
                }
            }
        });
    }

    @Override
    public void onGetGasStationsSuccess(@NonNull List<GasStation> gasStations) {
        if (!isAdded())
            return;
        for (GasStation gasStation : gasStations) {
            View gasStationView = getLayoutInflater().inflate(R.layout.item_expense_type, llGasStations, false);
            gasStationView.findViewById(R.id.img_expense_type).setVisibility(View.GONE);
            TextView gasStationName = gasStationView.findViewById(R.id.tv_expense_type_name);
            gasStationName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            gasStationName.setText(gasStation.getName());
            gasStationView.setTag(gasStation);
            gasStationView.setOnClickListener(view -> {
                GasStation gasStationSelected = (GasStation) view.getTag();
                tvGasStation.setText(gasStationSelected.getName());
                tvGasStation.setTextColor(Color.parseColor("#363636"));
                if (mExpense != null)
                    mExpense.setEnergigasStation(gasStationSelected.getName());

                svExpenseForm.smoothScrollTo(0, tvGasStation.getTop());
                cvGasStations.setVisibility(View.GONE);
            });
            llGasStations.addView(gasStationView);
        }
    }

    @Override
    public void onGetGasStationsError() {

    }

    private void openExpenseForm(Expense expense) {
        /* Hide expenses view */
        llExpenses.setVisibility(View.INVISIBLE);
        /* Get date format to show the date correctly */
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(getString(R.string.format_full_date_time), Locale.getDefault());
        if (expense != null) {
            mExpense = expense;
            /* Fill form views with the expense info */

            if (mExpense.getExpenseType() != null) {
                tvExpenseTypeSelected.setText(mExpense.getExpenseType().getDescription());
                /* Set expense type icon */
                tvExpenseTypeSelected.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_toll_small, 0, R.drawable.ic_down_arrow, 0);
                /* Check if it has expense sub type */
                fillExpenseSubTypesListView(mExpense.getExpenseType().getChildren());
                if (mExpense.getExpenseType().getChildren() != null && !mExpense.getExpenseType().getChildren().isEmpty()) {
                    if (mExpense.getExpenseSubType() != null)
                        tvExpenseSubTypeSelected.setText(mExpense.getExpenseSubType().getDescription());
                    tvExpenseSubTypeSelected.setVisibility(View.VISIBLE);
                } else {
                    tvExpenseSubTypeSelected.setVisibility(View.GONE);
                }
            }
            if (mExpense.getDate() != null)
                tvExpenseDate.setText(dateFormat.format(mExpense.getDate()));
            if (!TextUtils.isEmpty(mExpense.getRucNumber()) && !TextUtils.isEmpty(mExpense.getSupplierName())) {
                mSkipOnRucTextChanged = true;
                edtExpenseRuc.setText(String.format(Locale.getDefault(), "%s - %s", mExpense.getRucNumber(), mExpense.getSupplierName()));
            } else if (TextUtils.isEmpty(mExpense.getSupplierName())) {
                edtExpenseRuc.setText(mExpense.getRucNumber());
            }
            if (Expense.INVOICE_VOUCHER_TYPE.equals(mExpense.getVoucherType()))
                rdgVoucherType.check(R.id.rb_invoice);
            else if (Expense.BILL_VOUCHER_TYPE.equals(mExpense.getVoucherType()))
                rdgVoucherType.check(R.id.rb_bill);
            else
                rdgVoucherType.clearCheck();
            edtVoucherNumber.setText(mExpense.getVoucherNumber());
            if (mExpense.getAmount() != null)
                edtExpenseAmount.setText(String.format(Locale.getDefault(), "%.2f", mExpense.getAmount()));
            if (!TextUtils.isEmpty(mExpense.getObservation()))
                edtObservation.setText(mExpense.getObservation());

            /* Fields related to the fuel consumption expense type */
            if (mExpense.getExpenseType() != null &&
                    Constants.FUEL_CONSUMPTION_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) {
                tvVoucherType.setVisibility(View.GONE);
                rdgVoucherType.setVisibility(View.GONE);
                rdgFuelProvider.setVisibility(View.VISIBLE);
                rdgFuelProvider.check(mExpense.getExternalProvider() != null && mExpense.getExternalProvider() ?
                        R.id.rb_external : R.id.rb_energigas);
                if (mExpense.getEnergigasStation() != null) {
                    tvGasStation.setText(mExpense.getEnergigasStation());
                } else {
                    tvGasStation.setText(R.string.lbl_select_gas_station);
                }
                tvGallons.setVisibility(View.VISIBLE);
                tilGallons.setVisibility(View.VISIBLE);
                if (mExpense.getGallons() != null)
                    edtGallons.setText(String.format(Locale.getDefault(), "%.2f", mExpense.getGallons()));
                tvMileage.setVisibility(View.VISIBLE);
                tilMileage.setVisibility(View.VISIBLE);
                if (mExpense.getMileage() != null)
                    edtMileage.setText(String.format(Locale.getDefault(), "%d", mExpense.getMileage()));
            } else {
                tvVoucherType.setVisibility(View.VISIBLE);
                rdgVoucherType.setVisibility(View.VISIBLE);
                tvVoucherNumber.setText(R.string.lbl_voucher_number);
                rdgFuelProvider.setVisibility(View.GONE);
                rdgFuelProvider.clearCheck();
                if (mExpense != null)
                    mExpense.setExternalProvider(null);
                tvGasStation.setVisibility(View.GONE);
                cvGasStations.setVisibility(View.GONE);
                tvGasStation.setText(R.string.lbl_select_gas_station);
                if (mExpense != null)
                    mExpense.setEnergigasStation(null);
                tvGallons.setVisibility(View.GONE);
                tilGallons.setVisibility(View.GONE);
                edtGallons.setText("");
                tvMileage.setVisibility(View.GONE);
                tilMileage.setVisibility(View.GONE);
                edtMileage.setText("");
            }

        } else {
            mExpense = new Expense();
            tvExpenseSubTypeSelected.setVisibility(View.GONE);
            /* Set expense date to now */
            mExpense.setDate(new Date());
            tvExpenseDate.setText(dateFormat.format(mExpense.getDate()));
            /* Set default voucher type to "Factura" */
            mExpense.setVoucherType(rbInvoice.getText().toString());
        }
        /* Disable form controls if there is no expense type selected */
        if (mExpense.getExpenseType() == null || mExpense.getExpenseType().getId() == null) {
            disableViews(clExpenseDetailForm);
        } else {
            enableViews(clExpenseDetailForm);
        }
        /* Set ruc number (supplier) autocomplete adapter */
        setRucAutocompleteAdapter();
        /* Scroll form to top */
        svExpenseForm.scrollTo(0, 0);
        /* Show form */
        cvExpenseDetailForm.setVisibility(View.VISIBLE);
        /* Show photo */
        if (mExpense.getPhotoFilePath() != null) {
            svExpenseForm.post(() ->
                    Glide.with(getContext()).load(new File(mExpense.getPhotoFilePath()))
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgAttachPhoto)
            );
        }
    }

    @OnClick(R.id.btn_close_expense_form)
    void confirmCloseExpenseForm() {
        new MaterialDialog.Builder(getContext())
                .content(R.string.msg_confirm_close_form)
                .positiveText(R.string.lbl_accept)
                .onPositive((dialog, which) -> closeExpenseForm(false))
                .negativeText(R.string.lbl_cancel)
                .show();
    }

    private void closeExpenseForm(boolean expenseWasSaved) {
        if (!expenseWasSaved) {
            /* Delete unsaved photo */
            String unsavedPhotoFilePath = null;
            if ((mExpense.getId() == null && mExpense.getPhotoFilePath() != null) ||
                    (mExpense.getId() != null && mExpense.getPhotoFilePath() != null &&
                            !mExpense.getPhotoFilePath().equals(mExpenseRegistered.getPhotoFilePath()))) {
                unsavedPhotoFilePath = mExpense.getPhotoFilePath();
            }
            if (unsavedPhotoFilePath != null) {
                File unsavedPhotoFile = new File(unsavedPhotoFilePath);
                unsavedPhotoFile.delete();
            }
        }
        /* Set no expense being added or edited */
        mExpense = null;
        mExpenseRegistered = null;
        /* Hide form */
        cvExpenseDetailForm.setVisibility(View.GONE);
        /* Clear form views */
        tvExpenseTypeSelected.setText(R.string.lbl_select_expense_type);
        tvExpenseTypeSelected.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, R.drawable.ic_down_arrow, 0); // clear expense type icon
        cvExpenseTypes.setVisibility(View.GONE);
        tvExpenseSubTypeSelected.setText(R.string.lbl_select_expense_sub_type);
        tvExpenseSubTypeSelected.setTextColor(Color.parseColor("#363636"));
        cvExpenseSubTypes.setVisibility(View.GONE);
        tvExpenseDate.setText("");
        //edtExpenseRuc.removeTextChangedListener(mRucTextWatcher);
        edtExpenseRuc.setText("");
        tilExpenseRuc.setError(null);
        rdgVoucherType.check(R.id.rb_invoice); // "Factura" by default
        edtVoucherNumber.setText("");
        edtExpenseAmount.setText("");
        imgAttachPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        edtObservation.setText("");
        /* Fields related to the fuel consumption expense type */
        rdgFuelProvider.clearCheck();
        rdgFuelProvider.setVisibility(View.GONE);
        tvGasStation.setTextColor(Color.parseColor("#363636"));
        tvGasStation.setText(R.string.lbl_select_gas_station);
        tvGasStation.setVisibility(View.GONE);
        cvGasStations.setVisibility(View.GONE);
        tvGallons.setVisibility(View.GONE);
        tilGallons.setVisibility(View.GONE);
        edtGallons.setText("");
        tvMileage.setVisibility(View.GONE);
        tilMileage.setVisibility(View.GONE);
        edtMileage.setText("");
        /* Show expenses view */
        llExpenses.setVisibility(View.VISIBLE);
    }

    private void disableViews(@NonNull ViewGroup parentView) {
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View view = parentView.getChildAt(i);
            if (view.getId() != R.id.tv_expense_type_selected &&
                    view.getId() != R.id.cv_expense_types) {
                view.setEnabled(false);
                if (view instanceof ViewGroup)
                    disableViews((ViewGroup) view);
                else
                    view.setAlpha(0.29f);
            }
        }
    }

    private void enableViews(@NonNull ViewGroup parentView) {
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View view = parentView.getChildAt(i);
            if (view.getId() != R.id.tv_expense_type_selected &&
                    view.getId() != R.id.cv_expense_types) {
                view.setEnabled(true);
                if (view instanceof ViewGroup)
                    enableViews((ViewGroup) view);
                else
                    view.setAlpha(1f);
            }
        }
    }

    @Override
    public void onAddExpense() {
        openExpenseForm(null);
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

    @OnClick(R.id.tv_expense_type_selected)
    void toggleExpenseTypes() {
        if (cvExpenseTypes.getVisibility() == View.VISIBLE) {
            btnSaveExpense.setEnabled(true);
            cvExpenseTypes.setVisibility(View.GONE);
        } else {
            btnSaveExpense.setEnabled(false);
            cvExpenseTypes.setVisibility(View.VISIBLE);
            cvExpenseSubTypes.setVisibility(View.GONE);
            cvGasStations.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_expense_sub_type_selected)
    void toggleExpenseSubTypes() {
        if (cvExpenseSubTypes.getVisibility() == View.VISIBLE) {
            cvExpenseSubTypes.setVisibility(View.GONE);
        } else {
            cvExpenseSubTypes.setVisibility(View.VISIBLE);
            cvExpenseTypes.setVisibility(View.GONE);
            cvGasStations.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_gas_station)
    void toggleGasStations() {
        if (cvGasStations.getVisibility() == View.VISIBLE) {
            cvGasStations.setVisibility(View.GONE);
        } else {
            cvGasStations.setVisibility(View.VISIBLE);
            cvExpenseTypes.setVisibility(View.GONE);
            cvExpenseSubTypes.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_date_label, R.id.tv_expense_item_date_value, R.id.img_date})
    void showDateTimePicker() {
        DateUtil.chooseDateTime(getContext(),
                mExpense != null && mExpense.getDate() != null ? mExpense.getDate().getTime() : new Date().getTime(),
                dateTimeInMillis -> {
                    Date date = new Date(dateTimeInMillis);
                    mExpense.setDate(date);
                    SimpleDateFormat dateFormat =
                            new SimpleDateFormat(getString(R.string.format_full_date_time), Locale.getDefault());
                    tvExpenseDate.setText(dateFormat.format(mExpense.getDate()));
                });
    }

    @OnTextChanged(value = R.id.edt_ruc_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedRucNumber(Editable editable) {
        if (mSkipOnRucTextChanged) {
            mSkipOnRucTextChanged = false;
            return;
        }
        if (mExpense != null) {
            mExpense.setRucNumber(editable.toString());
            mExpense.setSupplierId(null);
            mExpense.setSupplierName(null);
        }
        tilExpenseRuc.setError(null);
    }

    @OnCheckedChanged({R.id.rb_invoice, R.id.rb_bill})
    void onVoucherTypeSelected(CompoundButton radioButton, boolean checked) {
        if (mExpense != null && checked)
            mExpense.setVoucherType(radioButton.getText().toString());
    }

    @OnTextChanged(value = R.id.edt_voucher_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedVoucherNumber(Editable editable) {
        if (mExpense != null)
            mExpense.setVoucherNumber(editable.toString());
        tilVoucherNumber.setError(null);
    }

    @OnTextChanged(value = R.id.edt_amount, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedAmount(Editable editable) {
        if (mExpense != null) {
            if (!TextUtils.isEmpty(editable)) {
                try {
                    mExpense.setAmount(Double.parseDouble(editable.toString()));
                } catch (NumberFormatException e) {
                    mExpense.setAmount(null);
                    editable.clear();
                }
            } else
                mExpense.setAmount(null);
        }
        tilAmount.setError(null);
    }

    @OnClick(R.id.img_attach_photo)
    void onClickCameraIcon() {
        mPreviousPhotoFilePath = mExpense.getPhotoFilePath();
        if (mPreviousPhotoFilePath != null) {
            List<Expense> expenses = new ArrayList<>();
            expenses.add(mExpense);
            mImageOverlayView = new ImageOverlayView(getContext());
            mImageOverlayView.setPhotoFilePath(mPreviousPhotoFilePath);
            StfalconImageViewer imageViewer = new StfalconImageViewer.Builder<>(getContext(), expenses,
                    (view, image) -> {
                        if (image.getPhotoFilePath() != null) {
                            Glide.with(getContext()).load(new File(image.getPhotoFilePath())).into(view);
                        }
                    })
                    .withTransitionFrom(imgAttachPhoto)
                    .withOverlayView(mImageOverlayView)
                    .withHiddenStatusBar(false)
                    .show();
            mImageOverlayView.setOnCameraClick(() -> {
                attachPhoto();
                imageViewer.dismiss();
            });
            mImageOverlayView.setOnDeleteClick(path -> {
                File photoFile = new File(path);
                boolean wasDeleted = photoFile.delete();
                //if (wasDeleted) {
                    mExpense.setPhotoFilePath(null);
                    Glide.with(getContext()).load(ContextCompat.getDrawable(getContext(), R.drawable.ic_camera))
                            .into(imgAttachPhoto);
                    imageViewer.updateTransitionImage(null);
                    imageViewer.dismiss();
                //}
            });
            return;
        }

        attachPhoto();
    }

    private void attachPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtil.createImageFile(getContext());
                mExpense.setPhotoFilePath(photoFile != null ? photoFile.getAbsolutePath() : null);
            } catch (IOException ex) {
                // Error occurred while creating the File
                LogUtil.e(TAG, "Error occurred while creating the photo file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "pe.focusit.energigas.fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @OnTextChanged(value = R.id.edt_observation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedObservation(Editable editable) {
        if (mExpense != null)
            mExpense.setObservation(editable.toString());
        tilObservation.setError(null);
    }

    @OnCheckedChanged({R.id.rb_energigas, R.id.rb_external})
    void onFuelProviderTypeSelected(CompoundButton radioButton, boolean checked) {
        if (mExpense != null && checked) {
            mExpense.setExternalProvider(radioButton.getId() == R.id.rb_external);
            if (mExpense.getExternalProvider()) {
                tvGasStation.setVisibility(View.GONE);
                tvVoucherNumber.setText(R.string.lbl_invoice_number);
            } else {
                tvVoucherNumber.setText(R.string.lbl_ticket_number);
                tvGasStation.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnTextChanged(value = R.id.edt_gallons, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedGallons(Editable editable) {
        if (mExpense != null) {
            if (!TextUtils.isEmpty(editable)) {
                try {
                    mExpense.setGallons(Double.parseDouble(editable.toString()));
                } catch (NumberFormatException e) {
                    mExpense.setGallons(null);
                    editable.clear();
                }
            } else
                mExpense.setGallons(null);
        }
        tilGallons.setError(null);
    }

    @OnTextChanged(value = R.id.edt_mileage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedMileage(Editable editable) {
        if (mExpense != null) {
            if (!TextUtils.isEmpty(editable))
                mExpense.setMileage(Integer.parseInt(editable.toString()));
            else
                mExpense.setMileage(null);
        }
        tilMileage.setError(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mExpense.getPhotoFilePath() != null) {
                Glide.with(getContext()).load(new File(mExpense.getPhotoFilePath()))
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(imgAttachPhoto);
                /* Delete previous photo file if exists */
                if (mPreviousPhotoFilePath != null) {
                    File photoFile = new File(mPreviousPhotoFilePath);
                    photoFile.delete();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_CANCELED) {
            /* Delete the file created */
            if (mExpense.getPhotoFilePath() != null) {
                File photoFile = new File(mExpense.getPhotoFilePath());
                photoFile.delete();
                mExpense.setPhotoFilePath(mPreviousPhotoFilePath);
            }
        }
    }

    @OnClick(R.id.btn_save_expense)
    void saveExpense() {

        if (mExpense == null)
            return;

        /* Validate fields */

        boolean allFieldsOk = true;

        if (Constants.FUEL_CONSUMPTION_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) {

            /* Validate mileage */
            Integer mileage = mExpense.getMileage();
            if (mileage == null) {
                tilMileage.setError("Ingresa el kilometraje.");
                allFieldsOk = false;
                edtMileage.requestFocus();
                svExpenseForm.smoothScrollTo(0, tvMileage.getTop());
            }

            /* Validate gallons */
            Double gallons = mExpense.getGallons();
            if (gallons == null) {
                tilGallons.setError("Ingresa los galones.");
                allFieldsOk = false;
                edtGallons.requestFocus();
                svExpenseForm.smoothScrollTo(0, tvGallons.getTop());
            }

            if (!mExpense.getExternalProvider()) {
                /* Validate gas station */
                if (TextUtils.isEmpty(mExpense.getEnergigasStation())) {
                    allFieldsOk = false;
                    tvGasStation.setTextColor(Color.RED);
                    svExpenseForm.smoothScrollTo(0, tvGasStation.getTop());
                }
            }

        }

        /* Validate amount */
        Double amount = mExpense.getAmount();
        if (amount == null) {
            tilAmount.setError("Ingresa el monto.");
            allFieldsOk = false;
            edtExpenseAmount.requestFocus();
            svExpenseForm.smoothScrollTo(0, tvAmount.getTop());
        }

        /* Validate voucher number */
        String voucherNumber = mExpense.getVoucherNumber();
        if (TextUtils.isEmpty(voucherNumber) &&
                !Constants.PARKING_LOT_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim()) &&
                !Constants.VIATICUM_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) { // voucher number  is not mandatory for parking lot or viaticum
            tilVoucherNumber.setError("Ingresa el número de comprobante.");
            allFieldsOk = false;
            edtVoucherNumber.requestFocus();
            svExpenseForm.smoothScrollTo(0, tvVoucherNumber.getTop());
        }

        /* Validate RUC number (supplier) */
        if (mExpense.getSupplierId() == null) {
            if (Constants.FUEL_CONSUMPTION_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) {
                /* When selecting fuel consumption expense type, the supplier must be selected from the suggestions list */
                tilExpenseRuc.setError("Selecciona un RUC (proveedor) existente.");
                allFieldsOk = false;
                edtExpenseRuc.requestFocus();
                svExpenseForm.smoothScrollTo(0, tvRucNumber.getTop());
            } else {
                /* When selecting a expense type different than fuel consumption, the ruc can be typed
                    (may not be selected from the suggestions list) */
                String rucNumber = mExpense.getRucNumber();
                if (TextUtils.isEmpty(rucNumber)) {
                    if (!Constants.PARKING_LOT_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim()) &&
                            !Constants.VIATICUM_ITEM_ID.equalsIgnoreCase(mExpense.getExpenseType().getName().trim())) { // ruc is not mandatory for parking lot or viaticum
                        tilExpenseRuc.setError("Ingresa el RUC del proveedor.");
                        allFieldsOk = false;
                        edtExpenseRuc.requestFocus();
                        svExpenseForm.smoothScrollTo(0, tvRucNumber.getTop());
                    }
                } else if (rucNumber.length() != 11 || !rucNumber.matches("[0-9]+")) {
                    tilExpenseRuc.setError("El número de RUC debe ser de 11 dígitos.");
                    allFieldsOk = false;
                    edtExpenseRuc.requestFocus();
                    svExpenseForm.smoothScrollTo(0, tvRucNumber.getTop());
                }
            }
        }

        /* Validate expense sub type */
        if (mExpense.getExpenseType().getChildren() != null && !mExpense.getExpenseType().getChildren().isEmpty() &&
            mExpense.getExpenseSubTypeId() == null) {
            allFieldsOk = false;
            tvExpenseSubTypeSelected.setTextColor(Color.RED);
            svExpenseForm.smoothScrollTo(0, tvExpenseSubTypeSelected.getTop());
        }

        /* Validate expense type */
        if (mExpense.getExpenseTypeId() == null) {
            allFieldsOk = false;
            tvExpenseTypeSelected.setTextColor(Color.RED);
            svExpenseForm.smoothScrollTo(0, tvExpenseTypeSelected.getTop());
        }

        if (allFieldsOk) {
            /* Send the Expense model to the controller to save it */
            mController.saveExpense(mExpense);
        }

    }

    @Override
    public void onSaveExpenseSuccess(Route route) {
        if (!isAdded())
            return;
        /* Set updated route (the budget balance was updated) */
        mMyRoute = route;
        /* Hide keyboard */
        CommonUtil.hideSoftKeyboard(getActivity());
        /* Close form */
        closeExpenseForm(true);
        /* Update action bar with the new route budget balance */
        setActionBarView(mMyRoute.getBudgetBalance());
        /* Show success message */
        Snackbar snackbar = Snackbar.make(getView(), R.string.msg_expense_saved_success, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        /* Update expense list */
        mController.getExpenses();
    }

    @Override
    public void onSaveExpenseError() {

    }

    @Override
    public void onEditExpense(Expense expense) {
        mExpenseRegistered = expense;
        /* Make a copy of the original expense object */
        Expense expenseCopy = new Expense();
        ParseUtil.parseObject(expense, expenseCopy);
        /* The copy represents the changes that will be applied to the expense */
        openExpenseForm(expenseCopy);
    }

    @Override
    public void onDeleteExpense(Expense expense) {
        /* Show confirmation message */
        new MaterialDialog.Builder(getContext())
                .content(R.string.msg_confirm_delete_expense)
                .positiveText(R.string.lbl_accept)
                .onPositive((dialog, which) -> {
                    /* Delete the expense */
                    mController.deleteExpense(expense);
                })
                .negativeText(R.string.lbl_cancel)
                .show();
    }

    @Override
    public void onDeleteExpenseSuccess() {
        if (!isAdded())
            return;
        /* Update action bar with the new route budget balance */
        setActionBarView(mMyRoute.getBudgetBalance());
        /* Show success message */
        Snackbar snackbar = Snackbar.make(getView(), R.string.msg_expense_removed_success, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        /* Update expense list */
        mController.getExpenses();
    }

    @Override
    public void onDeleteExpenseError() {

    }

    private void setActionBarView(double routeBudgetBalance) {
        View view = getLayoutInflater()
                .inflate(R.layout.toolbar_back_balance_info, mListener.getAppActionBar(), false);
        TextView infoText = view.findViewById(R.id.tv_action_bar_info);
        infoText.setText(mRouteSegment.getSegmentDescription());
        ImageView imgBack = view.findViewById(R.id.img_back_arrow);
        imgBack.setOnClickListener(v -> mListener.loadFragment(RouteDetailFragment.newInstance()));
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public int getTitleStringResource() {
        return R.string.lbl_expense_detail;
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

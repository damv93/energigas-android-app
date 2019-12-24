package pe.focusit.energigas.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.ProductDispatchController;
import pe.focusit.energigas.model.ProductDispatch;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.CommonUtil;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.DateUtil;
import pe.focusit.energigas.util.FileUtil;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.util.ParseUtil;
import pe.focusit.energigas.view.ProductDispatchView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDispatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDispatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDispatchFragment extends BaseFragment implements ProductDispatchView {

    private static String TAG = ProductDispatchFragment.class.getSimpleName();

    private static int REQUEST_IMAGE_CAPTURE = 2;
    private OnFragmentInteractionListener mListener;

    private ProductDispatchController mController;
    private RouteSegment mRouteSegment;
    private ProductDispatch mProductDispatch; // Dispatch being added or edited through the form
    private ProductDispatch mDispatchRegistered; // Dispatch already registered (completed) if exist
    private Date mRouteDepartureDate;
    private String mPreviousPhotoFilePath;

    @BindView(R.id.tv_indications)
    TextView tvIndications;
    @BindView(R.id.btn_add_dispatch)
    Button btnAddDispatch;
    @BindView(R.id.cv_product_dispatch_form)
    MaterialCardView cvProductDispatchForm;
    @BindView(R.id.sv_product_dispatch_form)
    ScrollView svDispatchForm;
    @BindView(R.id.tv_dispatch_place)
    TextView tvDispatchPlace;
    @BindView(R.id.tv_arrival)
    TextView tvArrivalDate;
    @BindView(R.id.tv_departure)
    TextView tvDepartureDate;
    @BindView(R.id.edt_client_name)
    EditText edtClientName;
    @BindView(R.id.tv_amount_requested)
    TextView tvAmountRequested;
    @BindView(R.id.til_amount_requested)
    TextInputLayout tilAmountRequested;
    @BindView(R.id.edt_amount_requested)
    EditText edtAmountRequested;
    @BindView(R.id.tv_fuel_price)
    TextView tvFuelPrice;
    @BindView(R.id.til_fuel_price)
    TextInputLayout tilFuelPrice;
    @BindView(R.id.edt_fuel_price)
    EditText edtFuelPrice;
    @BindView(R.id.tv_tank_percentage)
    TextView tvTankPercentage;
    @BindView(R.id.til_tank_percentage)
    TextInputLayout tilTankPercentage;
    @BindView(R.id.edt_tank_percentage)
    EditText edtTankPercentage;
    @BindView(R.id.tv_dispatched_amount)
    TextView tvDispatchedAmount;
    @BindView(R.id.til_dispatched_amount)
    TextInputLayout tilDispatchedAmount;
    @BindView(R.id.edt_dispatched_amount)
    EditText edtDispatchedAmount;
    @BindView(R.id.tv_hit)
    TextView tvHit;
    @BindView(R.id.til_hit)
    TextInputLayout tilHit;
    @BindView(R.id.edt_hit)
    EditText edtHit;
    @BindView(R.id.tv_guide_number)
    TextView tvGuideNumber;
    @BindView(R.id.til_guide_number)
    TextInputLayout tilGuideNumber;
    @BindView(R.id.edt_guide_number)
    EditText edtGuideNumber;
    @BindView(R.id.tv_invoice_number)
    TextView tvInvoiceNumber;
    @BindView(R.id.til_invoice_number)
    TextInputLayout tilInvoiceNumber;
    @BindView(R.id.edt_invoice_number)
    EditText edtInvoiceNumber;
    @BindView(R.id.tv_received_by)
    TextView tvReceivedBy;
    @BindView(R.id.til_received_by)
    TextInputLayout tilReceivedBy;
    @BindView(R.id.edt_received_by)
    EditText edtReceivedBy;
    @BindView(R.id.tv_amount_value)
    TextView tvAmountValue;
    @BindView(R.id.edt_observation)
    EditText edtObservation;
    @BindView(R.id.img_attach_photo)
    ImageView imgAttachPhoto;
    @BindView(R.id.ll_product_dispatch_completed)
    LinearLayout llDispatchCompleted;
    @BindView(R.id.tv_dispatch_completed_place)
    TextView tvDispatchCompletedPlace;
    @BindView(R.id.tv_dispatch_completed_date)
    TextView tvDispatchCompletedDate;
    @BindView(R.id.tv_user_congrats)
    TextView tvUserCongrats;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public ProductDispatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductDispatchFragment.
     */
    public static ProductDispatchFragment newInstance(@NonNull RouteSegment routeSegment) {
        ProductDispatchFragment fragment = new ProductDispatchFragment();
        fragment.setRouteSegment(routeSegment);
        return fragment;
    }

    private void setRouteSegment(RouteSegment segment) {
        mRouteSegment = segment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_dispatch, container, false);
        ButterKnife.bind(this, view);

        cvProductDispatchForm.setClipToOutline(false); // this is to correctly show the close (x) button

        mController = new ProductDispatchController(getContext(), this, mRouteSegment);
        /* Get Route departure date */
        mRouteDepartureDate = mController.getDepartureDate();
        /* Get the dispatch if it is already registered (completed) */
        mController.getProductDispatch();

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

    private void openDispatchForm(ProductDispatch productDispatch) {
        btnAddDispatch.setEnabled(false);
        /* Get date format to show the date correctly */
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(getString(R.string.format_full_date_time), Locale.getDefault());
        /* Show form info */
        tvDispatchPlace.setText(mRouteSegment.getStopPlace());
        if (mRouteSegment.getClient() != null)
            edtClientName.setText(mRouteSegment.getClient().getName());
        if (productDispatch != null) {
            mProductDispatch = productDispatch;
            /* Fill form views with the dispatch info */
            if (mProductDispatch.getDepartureDate() != null)
                tvDepartureDate.setText(dateFormat.format(mProductDispatch.getDepartureDate()));
            if (mProductDispatch.getArrivalDate() != null)
                tvArrivalDate.setText(dateFormat.format(mProductDispatch.getArrivalDate()));
            if (mProductDispatch.getRequestedAmount() != null)
                edtAmountRequested.setText(String.format(Locale.getDefault(), "%d", mProductDispatch.getRequestedAmount()));
            else {
                /* Set requested amount from the route segment */
                if (mRouteSegment.getRequestedAmount() != null)
                    edtAmountRequested.setText(String.format(Locale.getDefault(), "%d", mRouteSegment.getRequestedAmount()));
            }
            if (mProductDispatch.getFuelPrice() != null)
                edtFuelPrice.setText(String.format(Locale.getDefault(), "%.2f", mProductDispatch.getFuelPrice()));
            if (mProductDispatch.getTankPercentage() != null)
                edtTankPercentage.setText(String.format(Locale.getDefault(), "%d", mProductDispatch.getTankPercentage()));
            if (mProductDispatch.getDispatchedAmount() != null)
                edtDispatchedAmount.setText(String.format(Locale.getDefault(), "%.2f", mProductDispatch.getDispatchedAmount()));
            if (mProductDispatch.getHit() != null)
                edtHit.setText(mProductDispatch.getHit());
            edtGuideNumber.setText(mProductDispatch.getGuideNumber());
            edtInvoiceNumber.setText(mProductDispatch.getInvoiceNumber());
            if (mProductDispatch.getAmount() != null)
                tvAmountValue.setText(String.format(Locale.getDefault(), "%.2f", mProductDispatch.getAmount()));
            edtReceivedBy.setText(mProductDispatch.getReceivedBy());
            edtObservation.setText(mProductDispatch.getObservation());
        } else {
            mProductDispatch = new ProductDispatch();
            /* Set departure date to route departure date */
            if (mRouteDepartureDate != null) {
                mProductDispatch.setDepartureDate(mRouteDepartureDate);
                tvDepartureDate.setText(dateFormat.format(mRouteDepartureDate));
            }
            /* Set arrival date to now */
            mProductDispatch.setArrivalDate(new Date());
            tvArrivalDate.setText(dateFormat.format(mProductDispatch.getArrivalDate()));
            /* Set requested amount from the route segment */
            if (mRouteSegment.getRequestedAmount() != null)
                edtAmountRequested.setText(String.format(Locale.getDefault(), "%d", mRouteSegment.getRequestedAmount()));
        }
        /* Scroll form to top */
        svDispatchForm.scrollTo(0, 0);
        /* Show form */
        cvProductDispatchForm.setVisibility(View.VISIBLE);
        /* Show photo */
        if (mProductDispatch.getPhotoFilePath() != null) {
            svDispatchForm.post(() ->
                    Glide.with(getContext()).load(new File(mProductDispatch.getPhotoFilePath()))
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgAttachPhoto)
            );
        }
    }

    @OnClick(R.id.btn_close_dispatch_form)
    void confirmCloseDispatchForm() {
        new MaterialDialog.Builder(getContext())
                .content(R.string.msg_confirm_close_form)
                .positiveText(R.string.lbl_accept)
                .onPositive((dialog, which) -> closeDispatchForm(false))
                .negativeText(R.string.lbl_cancel)
                .show();
    }

    private void closeDispatchForm(boolean dispatchWasSaved) {
        btnAddDispatch.setEnabled(true);
        if (!dispatchWasSaved) {
            /* Delete unsaved photo */
            String unsavedPhotoFilePath = null;
            if ((mProductDispatch.getId() == null && mProductDispatch.getPhotoFilePath() != null) ||
                    (mProductDispatch.getId() != null && mProductDispatch.getPhotoFilePath() != null &&
                            !mProductDispatch.getPhotoFilePath().equals(mDispatchRegistered.getPhotoFilePath()))) {
                unsavedPhotoFilePath = mProductDispatch.getPhotoFilePath();
            }
            if (unsavedPhotoFilePath != null) {
                File unsavedPhotoFile = new File(unsavedPhotoFilePath);
                unsavedPhotoFile.delete();
            }
        }
        mProductDispatch = null; // No dispatch being added or edited
        cvProductDispatchForm.setVisibility(View.GONE);
        /* Clear form views */
        edtAmountRequested.setText("");
        edtFuelPrice.setText("");
        tvAmountValue.setText("");
        edtTankPercentage.setText("");
        edtDispatchedAmount.setText("");
        edtHit.setText("");
        edtGuideNumber.setText("");
        edtInvoiceNumber.setText("");
        edtReceivedBy.setText("");
        edtObservation.setText("");
        imgAttachPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
    }

    @OnClick(R.id.btn_add_dispatch)
    void addDispatch() {
        openDispatchForm(null);
    }

    @OnClick({R.id.tv_arrival_label, R.id.tv_arrival, R.id.img_arrival})
    void showDateTimePicker() {
        DateUtil.chooseDateTime(getContext(),
                mProductDispatch != null && mProductDispatch.getArrivalDate() != null ?
                        mProductDispatch.getArrivalDate().getTime() : new Date().getTime(),
                dateTimeInMillis -> {
                    Date date = new Date(dateTimeInMillis);
                    mProductDispatch.setArrivalDate(date);
                    SimpleDateFormat dateFormat =
                            new SimpleDateFormat(getString(R.string.format_full_date_time), Locale.getDefault());
                    tvArrivalDate.setText(dateFormat.format(mProductDispatch.getArrivalDate()));
                });
    }

    @OnTextChanged(value = R.id.edt_amount_requested, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedAmountRequested(Editable editable) {
        if (mProductDispatch != null) {
            if (!TextUtils.isEmpty(editable))
                mProductDispatch.setRequestedAmount(Integer.parseInt(editable.toString()));
            else
                mProductDispatch.setRequestedAmount(null);
        }
        tilAmountRequested.setError(null);
    }

    @OnTextChanged(value = R.id.edt_fuel_price, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedFuelPrice(Editable editable) {
        if (mProductDispatch != null) {
            if (!TextUtils.isEmpty(editable)) {
                try {
                    double fuelPrice = Double.parseDouble(editable.toString());
                    mProductDispatch.setFuelPrice(fuelPrice);
                    if (mProductDispatch.getDispatchedAmount() != null) {
                        double totalPrice = fuelPrice * mProductDispatch.getDispatchedAmount();
                        tvAmountValue.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
                    } else {
                        tvAmountValue.setText("");
                    }
                } catch (NumberFormatException e) {
                    mProductDispatch.setFuelPrice(null);
                    tvAmountValue.setText("");
                    editable.clear();
                }
            } else {
                mProductDispatch.setFuelPrice(null);
                tvAmountValue.setText("");
            }
        }
        tilFuelPrice.setError(null);
    }

    @OnTextChanged(value = R.id.edt_tank_percentage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedTankPercentage(Editable editable) {
        if (mProductDispatch != null) {
            if (!TextUtils.isEmpty(editable))
                mProductDispatch.setTankPercentage(Integer.parseInt(editable.toString()));
            else
                mProductDispatch.setTankPercentage(null);
        }
        tilTankPercentage.setError(null);
    }

    @OnTextChanged(value = R.id.edt_dispatched_amount, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedDispatchedAmount(Editable editable) {
        if (mProductDispatch != null) {
            if (!TextUtils.isEmpty(editable)) {
                try {
                    double dispatchedAmount = Double.parseDouble(editable.toString());
                    mProductDispatch.setDispatchedAmount(dispatchedAmount);
                    if (mProductDispatch.getFuelPrice() != null) {
                        double totalPrice = dispatchedAmount * mProductDispatch.getFuelPrice();
                        tvAmountValue.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
                    } else {
                        tvAmountValue.setText("");
                    }
                } catch (NumberFormatException e) {
                    mProductDispatch.setDispatchedAmount(null);
                    tvAmountValue.setText("");
                    editable.clear();
                }
            } else {
                mProductDispatch.setDispatchedAmount(null);
                tvAmountValue.setText("");
            }
        }
        tilDispatchedAmount.setError(null);
    }

    @OnTextChanged(value = R.id.edt_hit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedHit(Editable editable) {
        if (mProductDispatch != null)
            mProductDispatch.setHit(editable.toString());
        tilHit.setError(null);
    }

    @OnTextChanged(value = R.id.edt_guide_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedGuideNumber(Editable editable) {
        if (mProductDispatch != null)
            mProductDispatch.setGuideNumber(editable.toString());
        tilGuideNumber.setError(null);
    }

    @OnTextChanged(value = R.id.edt_invoice_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedInvoiceNumber(Editable editable) {
        if (mProductDispatch != null)
            mProductDispatch.setInvoiceNumber(editable.toString());
        tilInvoiceNumber.setError(null);
    }

    @OnTextChanged(value = R.id.edt_received_by, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedReceivedBy(Editable editable) {
        if (mProductDispatch != null)
            mProductDispatch.setReceivedBy(editable.toString());
        tilReceivedBy.setError(null);
    }

    @OnTextChanged(value = R.id.edt_observation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChangedObservation(Editable editable) {
        if (mProductDispatch != null)
            mProductDispatch.setObservation(editable.toString());
    }

    @OnClick(R.id.img_attach_photo)
    void attachPhoto() {
        mPreviousPhotoFilePath = mProductDispatch.getPhotoFilePath();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtil.createImageFile(getContext());
                mProductDispatch.setPhotoFilePath(photoFile.getAbsolutePath());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            svDispatchForm.fullScroll(ScrollView.FOCUS_DOWN); // fixes bug
            if (mProductDispatch.getPhotoFilePath() != null) {
                Glide.with(getContext()).load(new File(mProductDispatch.getPhotoFilePath()))
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
            if (mProductDispatch.getPhotoFilePath() != null) {
                File photoFile = new File(mProductDispatch.getPhotoFilePath());
                photoFile.delete();
                mProductDispatch.setPhotoFilePath(mPreviousPhotoFilePath);
            }
        }
    }

    @Override
    public void onGetDispatchSuccess(ProductDispatch productDispatch) {
        if (productDispatch == null) {
            llDispatchCompleted.setVisibility(View.GONE);
            tvIndications.setVisibility(View.VISIBLE);
            btnAddDispatch.setVisibility(View.VISIBLE);
            return;
        }
        /* Set dispatch registered */
        mDispatchRegistered = productDispatch;
        /* Show dispatch info */
        tvDispatchCompletedPlace.setText(mRouteSegment.getStopPlace());
        if (mDispatchRegistered.getDepartureDate() != null)
            tvDispatchCompletedDate.setText(
                    new SimpleDateFormat(getString(R.string.format_short_date), Locale.getDefault())
                            .format(mDispatchRegistered.getDepartureDate()));
        tvUserCongrats.setText(getString(R.string.txt_user_congrats, mController.getUser().getName()));
        llDispatchCompleted.setVisibility(View.VISIBLE);
        tvIndications.setVisibility(View.GONE);
        btnAddDispatch.setVisibility(View.GONE);
    }

    @Override
    public void onGetDispatchError() {
        llDispatchCompleted.setVisibility(View.GONE);
        tvIndications.setVisibility(View.VISIBLE);
        btnAddDispatch.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_save_product_dispatch)
    void saveProductDispatch() {
        if (mProductDispatch == null)
            return;

        /* Validate fields */

        boolean allFieldsOk = true;

        /* Validate "Received by" field */
        String receivedBy = mProductDispatch.getReceivedBy();
        if (TextUtils.isEmpty(receivedBy)) {
            tilReceivedBy.setError("Ingresa el nombre de la persona.");
            allFieldsOk = false;
            edtReceivedBy.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvReceivedBy.getTop());
        }

        /* Validate invoice number */
        String invoiceNumber = mProductDispatch.getInvoiceNumber();
        if (TextUtils.isEmpty(invoiceNumber) && !isEnergigasClient()) {
            tilInvoiceNumber.setError("Ingresa el número de la factura.");
            allFieldsOk = false;
            edtInvoiceNumber.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvInvoiceNumber.getTop());
        }

        /* Validate guide number */
        String guideNumber = mProductDispatch.getGuideNumber();
        if (TextUtils.isEmpty(guideNumber)) {
            tilGuideNumber.setError("Ingresa el número de la guía.");
            allFieldsOk = false;
            edtGuideNumber.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvGuideNumber.getTop());
        }

        /* Validate hit */
        String hit = mProductDispatch.getHit();
        if (TextUtils.isEmpty(hit)) {
            tilHit.setError("Ingresa el golpe.");
            allFieldsOk = false;
            edtHit.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvHit.getTop());
        }

        /* Validate dispatched amount */
        Double dispatchedAmount = mProductDispatch.getDispatchedAmount();
        if (dispatchedAmount == null) {
            tilDispatchedAmount.setError("Ingresa la cantidad atendida.");
            allFieldsOk = false;
            edtDispatchedAmount.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvDispatchedAmount.getTop());
        }

        /* Validate tank percentage */
        Integer tankPercentage = mProductDispatch.getTankPercentage();
        if (tankPercentage == null) {
            tilTankPercentage.setError("Ingresa el porcentaje del tanque.");
            allFieldsOk = false;
            edtTankPercentage.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvTankPercentage.getTop());
        }

        /* Validate fuel price */
        Double fuelPrice = mProductDispatch.getFuelPrice();
        if (fuelPrice == null && !isEnergigasClient()) {
            tilFuelPrice.setError("Ingresa el precio del combustible.");
            allFieldsOk = false;
            edtFuelPrice.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvFuelPrice.getTop());
        }

        /* Validate requested amount*/
        Integer requestedAmount = mProductDispatch.getRequestedAmount();
        if (requestedAmount == null) {
            tilAmountRequested.setError("Ingresa la cantidad solicitada.");
            allFieldsOk = false;
            edtAmountRequested.requestFocus();
            svDispatchForm.smoothScrollTo(0, tvAmountRequested.getTop());
        }

        if (allFieldsOk) {
            /* Send the Product dispatch model to the controller to save it */
            mController.saveProductDispatch(mProductDispatch);
        }
    }

    @Override
    public void onSaveDispatchSuccess() {
        if (!isAdded())
            return;
        /* Hide keyboard */
        CommonUtil.hideSoftKeyboard(getActivity());
        /* Close form */
        closeDispatchForm(true);
        /* Show success message */
        Snackbar snackbar = Snackbar.make(getView(), R.string.msg_dispatch_saved_success, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        /* Update dispatch to show */
        mController.getProductDispatch();
    }

    @Override
    public void onSaveDispatchError() {

    }

    @OnClick(R.id.cv_product_dispatch_completed)
    void editDispatch() {
        /* Make a copy of the original dispatch registered */
        ProductDispatch dispatchCopy = new ProductDispatch();
        ParseUtil.parseObject(mDispatchRegistered, dispatchCopy);
        /* The copy represents the changes that will be applied to the original dispatch */
        openDispatchForm(dispatchCopy);
    }

    @OnClick(R.id.img_dispatch_completed_remove)
    void deleteDispatch() {
        if (mDispatchRegistered == null)
            return;

        /* Show confirmation message */
        new MaterialDialog.Builder(getContext())
                .content(R.string.msg_confirm_delete_dispatch)
                .positiveText(R.string.lbl_accept)
                .onPositive((dialog, which) -> {
                    /* Delete the dispatch registered */
                    mController.deleteProductDispatch(mDispatchRegistered);
                })
                .negativeText(R.string.lbl_cancel)
                .show();
    }

    @Override
    public void onDeleteDispatchSuccess() {
        if (!isAdded())
            return;
        /* Show success message */
        Snackbar snackbar = Snackbar.make(getView(), R.string.msg_dispatch_removed_success, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        /* Update dispatch to show */
        mController.getProductDispatch();
    }

    @Override
    public void onDeleteDispatchError() {

    }

    @OnClick(R.id.btn_close_declaration)
    void confirmCloseDeclaration() {
        /* Show confirmation message */
        new MaterialDialog.Builder(getContext())
                .content(R.string.msg_confirm_close_dispatch)
                .positiveText(R.string.lbl_accept)
                .onPositive((dialog, which) -> mController.checkExpenses())
                .negativeText(R.string.lbl_cancel)
                .show();
    }

    @Override
    public void onExpensesChecked(boolean hasExpenses) {
        if (!isAdded())
            return;
        if (hasExpenses) {
            mController.closeSegmentDeclaration();
        } else {
            new MaterialDialog.Builder(getContext())
                    .content(R.string.msg_no_expenses)
                    .positiveText(R.string.lbl_accept)
                    .onPositive((dialog, which) -> mController.closeSegmentDeclaration())
                    .negativeText(R.string.lbl_cancel)
                    .show();
        }
    }

    @Override
    public void onCloseSegmentDeclarationSuccess() {
        if (isAdded() && mListener != null)
            mListener.loadFragment(RouteDetailFragment.newInstance());
    }

    @Override
    public void onCloseSegmentDeclarationError(String errorMessage) {
        if (isAdded()) {
            showMessage("", errorMessage);
        }
    }

    private boolean isEnergigasClient() {
        return mRouteSegment.getClient() != null &&
                Constants.ENERGIGAS_RUC.equalsIgnoreCase(mRouteSegment.getClient().getRuc());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mController = null;
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
    public int getTitleStringResource() {
        return R.string.lbl_product_dispatch;
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
    }
}

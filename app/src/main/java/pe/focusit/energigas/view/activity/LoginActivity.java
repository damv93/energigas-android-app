package pe.focusit.energigas.view.activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.LoginController;
import pe.focusit.energigas.data.net.RestBase;
import pe.focusit.energigas.util.ClickLink;
import pe.focusit.energigas.view.LoginView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

public class LoginActivity extends BaseActivity implements LoginView {

    private LoginController mLoginController;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_user_doc_number)
    EditText edtUserDocNumber;
    @BindView(R.id.cb_legal_terms)
    CheckBox cbLegalTerms;
    @BindView(R.id.tv_legal_terms)
    TextView tvLegalTerms;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tvLegalTerms.setMovementMethod(new ClickLink() {
            @Override
            public void onLinkClick(String url) {
                showLegalTerms();
            }
        });

        mLoginController = new LoginController(this, this);
    }

    @OnTextChanged(value = {R.id.edt_username, R.id.edt_user_doc_number},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedUserCredentials() {
        btnLogin.setEnabled(!TextUtils.isEmpty(edtUsername.getText()) &&
                !TextUtils.isEmpty(edtUserDocNumber.getText()));
    }

    @OnClick(R.id.btn_login)
    public void login() {
        if (!cbLegalTerms.isChecked()) {
            new MaterialDialog.Builder(this)
                    .content(R.string.msg_accept_legal_terms)
                    .positiveText(R.string.lbl_accept)
                    .show();
        } else {
            btnLogin.setEnabled(false);
            mLoginController.login(edtUsername.getText().toString(), edtUserDocNumber.getText().toString());
        }
    }

    @Override
    public void onLoginSuccess() {
        /* Go to Main activity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String errorMessage) {
        btnLogin.setEnabled(true);
        showMessage(getString(R.string.lbl_error), errorMessage);
    }

    private void showLegalTerms() {
        showLoading();
        WebView wv = new WebView(this);
        wv.loadUrl(RestBase.LEGAL_TERMS_URL);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                new MaterialDialog.Builder(LoginActivity.this)
                        .customView(view, false)
                        .negativeText(R.string.lbl_close)
                        .show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginController = null;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

}

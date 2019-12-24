package pe.focusit.energigas.view.activity;

import com.afollestad.materialdialogs.MaterialDialog;

import androidx.appcompat.app.AppCompatActivity;
import pe.focusit.energigas.R;

public abstract class BaseActivity extends AppCompatActivity {

    public void showMessage(String title, String message) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(R.string.lbl_accept)
                .show();
    }

}

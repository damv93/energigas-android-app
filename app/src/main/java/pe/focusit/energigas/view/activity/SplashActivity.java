package pe.focusit.energigas.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import pe.focusit.energigas.controller.SplashController;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private SplashController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = new SplashController(this);
        Intent intent;
        if (mController.getUser() != null) {
            /* User is logged in, go to Main activity */
            intent = new Intent(this, MainActivity.class);
        } else {
            /* User is not logged in, go to Login activity */
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}

package pe.focusit.energigas.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.MainController;
import pe.focusit.energigas.model.User;
import pe.focusit.energigas.view.MainView;
import pe.focusit.energigas.view.fragment.BaseFragment;
import pe.focusit.energigas.view.fragment.DeclarationStatusFragment;
import pe.focusit.energigas.view.fragment.DeclarationsFragment;
import pe.focusit.energigas.view.fragment.ExpenseDetailFragment;
import pe.focusit.energigas.view.fragment.MyProfileFragment;
import pe.focusit.energigas.view.fragment.MyRouteFragment;
import pe.focusit.energigas.view.fragment.ProductDispatchFragment;
import pe.focusit.energigas.view.fragment.RouteDetailFragment;
import pe.focusit.energigas.view.fragment.SegmentDetailFragment;
import pe.focusit.energigas.view.fragment.SyncFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends BaseActivity implements
        MainView,
        MyRouteFragment.OnFragmentInteractionListener,
        SegmentDetailFragment.OnFragmentInteractionListener,
        RouteDetailFragment.OnFragmentInteractionListener,
        MyProfileFragment.OnFragmentInteractionListener,
        ProductDispatchFragment.OnFragmentInteractionListener,
        SyncFragment.OnFragmentInteractionListener,
        DeclarationsFragment.OnFragmentInteractionListener,
        DeclarationStatusFragment.OnFragmentInteractionListener,
        ExpenseDetailFragment.OnFragmentInteractionListener {

    private final static int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 0;

    private MainController mController;
    private BaseFragment mCurrentFragment;

    @BindView(R.id.toolbar_app)
    Toolbar toolbar;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adjustBottomNavIconSizes();

        mController = new MainController(this, this);

        setActionBarView();

        /* Check if data is synced */
        if (mController.isSynced()) {
            bnvMain.setOnNavigationItemSelectedListener(menuItem -> {
                BaseFragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_item_declarations:
                        selectedFragment = DeclarationsFragment.newInstance();
                        break;
                    case R.id.nav_item_my_route:
                        selectedFragment = MyRouteFragment.newInstance();
                        break;
                    case R.id.nav_item_my_profile:
                        selectedFragment = MyProfileFragment.newInstance();
                        break;
                }
                loadFragment(selectedFragment);
                return true;
            });
            bnvMain.setSelectedItemId(R.id.nav_item_my_route);
        } else {
            loadFragment(SyncFragment.newInstance());
        }

    }

    private void adjustBottomNavIconSizes() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bnvMain.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(com.google.android.material.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            switch (i) {
                case 0:
                    /* "Declarations" icon */
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
                    break;
                case 1:
                    /* "My route" icon */
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, displayMetrics);
                    break;
                case 2:
                    /* "My profile" icon */
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, displayMetrics);
                    break;
            }
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }

    private void setActionBarView() {
        View view = getLayoutInflater().inflate(R.layout.toolbar_driver_info, toolbar, false);
        User user = mController.getUser();
        TextView tvDriverName = view.findViewById(R.id.tv_driver_name);
        tvDriverName.setText(user.getFullName());
        toolbar.removeAllViews();
        toolbar.addView(view);
    }

    @Override
    public void loadFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frl_main, fragment);
        transaction.commit();
        mCurrentFragment = fragment;
    }

    @Override
    public Toolbar getAppActionBar() {
        return toolbar;
    }

    @Override
    public void setActionBarView(View view) {
        if (view != null) {
            toolbar.removeAllViews();
            toolbar.addView(view);
        } else
            setActionBarView();
    }

    @Override
    public void onSyncSuccess() {
        /* Once data is synced, set the navigation item listeners and load 'My route' fragment */
        bnvMain.setOnNavigationItemSelectedListener(menuItem -> {
            BaseFragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_item_declarations:
                    selectedFragment = DeclarationsFragment.newInstance();
                    break;
                case R.id.nav_item_my_route:
                    selectedFragment = MyRouteFragment.newInstance();
                    break;
                case R.id.nav_item_my_profile:
                    selectedFragment = MyProfileFragment.newInstance();
                    break;
            }
            loadFragment(selectedFragment);
            return true;
        });
        bnvMain.setSelectedItemId(R.id.nav_item_my_route);
    }

    @Override
    public void logout() {
        mController.logout();
    }

    @Override
    public void onLogoutSuccess() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onLogoutError(String errorMessage) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void generateDbBackup() {
        requestPermissionToGenerateDBBackup();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                SharedPrefs sp = new SharedPrefs(this);
                sp.remove(Constants.SharedKey.USER_ID);
                sp.remove(Constants.SharedKey.USER_TOKEN);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            case R.id.menu_item_bd_backup:
                requestPermissionToGenerateDBBackup();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void requestPermissionToGenerateDBBackup() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            //       Manifest.permission.READ_CONTACTS)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            //} else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            //}
        } else {
            // Permission has already been granted
            doGenerateDbBackup();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    doGenerateDbBackup();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    protected void doGenerateDbBackup() {
        try {
            String lsNameBD = getString(R.string.bd_name);

            //Get Path database
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + lsNameBD;
                String backupDBPath = "energigas.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    if (backupDB.exists()) {
                        backupDB.delete();
                    }
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment == null || !mCurrentFragment.onBackPressed())
            super.onBackPressed();
    }
}

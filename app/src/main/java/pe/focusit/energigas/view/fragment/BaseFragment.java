package pe.focusit.energigas.view.fragment;

import androidx.fragment.app.Fragment;
import pe.focusit.energigas.view.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    public void showMessage(String title, String message) {
        ((BaseActivity) getActivity()).showMessage(title, message);
    }

    public int getTitleStringResource() {
        return 0;
    }

    public boolean onBackPressed() {
        return false;
    }

}

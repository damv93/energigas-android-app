package pe.focusit.energigas.view.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import pe.focusit.energigas.view.fragment.BaseFragment;

public class DeclarationsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<BaseFragment> mFragmentList = new ArrayList<>();

    public DeclarationsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(BaseFragment fragment) {
        mFragmentList.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(getItem(position).getTitleStringResource());
    }
}

package pe.focusit.energigas.util;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import androidx.annotation.NonNull;

public abstract class Adapter<T> extends BaseAdapter {

    private final List<T> mList;
    private final int mResource;
    private final Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    protected Adapter(@NonNull Context context, int layout, @NonNull List<T> list, @NonNull T hint) {
        super();
        this.mContext = context;
        this.mResource = layout;
        this.mList = list;
        this.mList.add(hint);
        this.mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.mResource, parent, false);
        }
        create(getItem(position), view);
        return view;
    }

    @Override
    public int getCount() {
        /* Count is equal to the list size minus one because of the hint item */
        return mList == null || mList.isEmpty() ? 0 : mList.size() - 1;
    }

    @Override
    public T getItem(int position) {
        return this.mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPosition(Object item) {
        return this.mList.indexOf(item);
    }

    private void selectView(int position, boolean value) {
        if (value)
            this.mSelectedItemsIds.put(position, value);
        else
            this.mSelectedItemsIds.delete(position);

        this.notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return this.mSelectedItemsIds.get(position);
    }

    public int getSelectedCount() {
        return this.mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return this.mSelectedItemsIds;
    }

    public abstract void create(T item, View view);
}

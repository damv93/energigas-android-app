package pe.focusit.energigas.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.model.Deposit;

public class DepositsAdapter extends RecyclerView.Adapter<DepositsAdapter.DepositViewHolder> {

    private List<Deposit> mDeposits = new ArrayList<>();

    public void setData(List<Deposit> deposits) {
        mDeposits = deposits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DepositViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deposit, parent, false);
        return new DepositViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepositViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDeposits != null ? mDeposits.size() : 0;
    }

    public class DepositViewHolder extends RecyclerView.ViewHolder {

        public DepositViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

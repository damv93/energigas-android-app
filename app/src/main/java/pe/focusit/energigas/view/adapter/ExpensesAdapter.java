package pe.focusit.energigas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.focusit.energigas.R;
import pe.focusit.energigas.model.Expense;
import pe.focusit.energigas.model.RouteSegment;

public class ExpensesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int EXPENSE_ITEM_VIEW_TYPE = 0;
    private static int FOOTER_ITEM_VIEW_TYPE = 1;

    private List<Expense> mExpenseList;
    private RouteSegment mRouteSegment;
    private OnExpenseInteractionListener mListener;

    public ExpensesAdapter(@NonNull RouteSegment routeSegment,
                           @NonNull OnExpenseInteractionListener onExpenseInteractionListener) {
        mRouteSegment = routeSegment;
        mListener = onExpenseInteractionListener;
        mExpenseList = new ArrayList<>();
        /* Add "footer item" (total view + add button) */
        mExpenseList.add(new Expense());
    }

    public void setData(List<Expense> expenseList) {
        mExpenseList = expenseList;
        if (mExpenseList == null) {
            mExpenseList = new ArrayList<>();
        }
        /* Add "footer item" (total view + add button) */
        mExpenseList.add(new Expense());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == FOOTER_ITEM_VIEW_TYPE) {
            View view = inflater.inflate(R.layout.item_expense_footer, parent, false);
            return new ExpensesAdapter.FooterViewHolder(view);
        }
        View view = inflater.inflate(R.layout.item_expense, parent, false);
        return new ExpensesAdapter.ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if (holder instanceof ExpenseViewHolder) {
            Expense expense = mExpenseList.get(position);
            ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder) holder;
            /* Set expense description text view */
            if (expense.getExpenseType() != null)
                expenseViewHolder.tvExpenseName.setText(expense.getExpenseType().getDescription());
            /* Set expense date text view */
            expenseViewHolder.tvExpenseDate.setText(
                    new SimpleDateFormat(context.getString(R.string.format_short_date), Locale.getDefault())
                            .format(expense.getDate()));
            /* Set expense amount text view */
            expenseViewHolder.tvExpenseAmount.setText(
                    context.getString(R.string.lbl_expense_amount_pen, expense.getAmount()));
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            /* Calculate the expenses total amount */
            double total = 0;
            for (Expense expense : mExpenseList) {
                Double amount = expense.getAmount();
                if (amount != null)
                    total += amount;
            }
            /* Set the expenses total amount */
            footerViewHolder.tvExpenseTotal.setText(context.getString(R.string.lbl_expense_amount_pen, total));
            /* Show or hide the total card view */
            footerViewHolder.cvExpenseTotal.setVisibility(getExpenseItemCount() <= 0 ? View.GONE : View.VISIBLE);
            footerViewHolder.tvSegmentExpense.setVisibility(getExpenseItemCount() <= 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mExpenseList == null ? 0 : mExpenseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        /* The last item is the footer item (total view + add button) */
        return mExpenseList != null && position == mExpenseList.size() - 1 ?
                FOOTER_ITEM_VIEW_TYPE : EXPENSE_ITEM_VIEW_TYPE;
    }

    public int getExpenseItemCount() {
        /* The number of expense items is equal to the item count minus one because of the footer item */
        int itemCount = getItemCount();
        return itemCount > 0 ? itemCount - 1 : 0;
    }

    public interface OnExpenseInteractionListener {
        void onAddExpense();
        void onEditExpense(Expense expense);
        void onDeleteExpense(Expense expense);
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_expense_item_name)
        TextView tvExpenseName;
        @BindView(R.id.tv_expense_item_date)
        TextView tvExpenseDate;
        @BindView(R.id.tv_expense_item_amount)
        TextView tvExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_expense_item)
        void onEditExpense() {
            Expense expense = mExpenseList.get(getAdapterPosition());
            mListener.onEditExpense(expense);
        }

        @OnClick(R.id.img_expense_item_remove)
        void onDeleteExpense() {
            Expense expense = mExpenseList.get(getAdapterPosition());
            mListener.onDeleteExpense(expense);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cv_expense_total)
        MaterialCardView cvExpenseTotal;
        @BindView(R.id.tv_expense_total_value)
        TextView tvExpenseTotal;
        @BindView(R.id.tv_segment_expense)
        TextView tvSegmentExpense;
        @BindView(R.id.btn_add_expense)
        Button btnAddExpense;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvSegmentExpense.setText(itemView.getContext().getString(
                    R.string.txt_segment_expense, mRouteSegment.getSegmentDescription()));
            btnAddExpense.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onAddExpense();
        }

    }

}

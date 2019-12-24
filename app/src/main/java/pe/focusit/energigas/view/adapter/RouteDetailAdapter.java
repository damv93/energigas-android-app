package pe.focusit.energigas.view.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.focusit.energigas.R;
import pe.focusit.energigas.model.ProductDispatch;
import pe.focusit.energigas.model.RouteSegment;

public class RouteDetailAdapter extends RecyclerView.Adapter<RouteDetailAdapter.RouteSegmentViewHolder> {

    private List<RouteSegment> mRouteSegmentList = new ArrayList<>();
    private OnGoToSegmentDetailListener mListener;

    public RouteDetailAdapter(OnGoToSegmentDetailListener listener) {
        mListener = listener;
    }

    public void setData(List<RouteSegment> routeSegmentList) {
        mRouteSegmentList = routeSegmentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RouteSegmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_detail, parent, false);
        return new RouteSegmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteSegmentViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        RouteSegment routeSegment = mRouteSegmentList.get(position);
        holder.tvSegmentNumber.setText(String.format(Locale.getDefault(),"%02d", routeSegment.getStopNumber()));
        holder.tvRouteSegment.setText(routeSegment.getSegmentDescription());
        int progressPercentage = routeSegment.getProgressPercentage();
        holder.tvProgressPercentage.setText(context.getString(R.string.lbl_progress_percentage, progressPercentage));
        double expenseAmount = routeSegment.getExpenseAmount();
        holder.tvExpenseAmount.setText(context.getString(R.string.lbl_expense_amount_number, expenseAmount));

        /* Check if the product dispatch of the route segment is closed */
        ProductDispatch dispatch = routeSegment.getProductDispatch();
        boolean isDispatchClosed = (dispatch == null || dispatch.getDispatchClosed() == null) ?
                false : dispatch.getDispatchClosed();
        if (isDispatchClosed) {
            /* If the dispatch of the segment is closed, don't allow entering to the segment info anymore */
            holder.imgEnter.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(null);
            String disabledColor = "#a2a2a2";
            ImageViewCompat.setImageTintList(holder.imgPin, ColorStateList.valueOf(Color.parseColor(disabledColor)));
            holder.imgPin.setAlpha(0.72f);
            holder.tvRouteSegment.setTextColor(Color.parseColor(disabledColor));
            holder.tvProgressPercentage.setTextColor(Color.parseColor(disabledColor));
            holder.tvExpenseAmount.setTextColor(Color.parseColor(disabledColor));
        } else {
            holder.itemView.setOnClickListener(view -> mListener.onGoToSegmentDetail(routeSegment));
        }
    }

    @Override
    public int getItemCount() {
        return mRouteSegmentList == null ? 0 : mRouteSegmentList.size();
    }

    public interface OnGoToSegmentDetailListener {
        void onGoToSegmentDetail(RouteSegment routeSegment);
    }

    public class RouteSegmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_segment_number)
        TextView tvSegmentNumber;
        @BindView(R.id.img_pin)
        ImageView imgPin;
        @BindView(R.id.tv_route_segment)
        TextView tvRouteSegment;
        @BindView(R.id.tv_progress_percentage)
        TextView tvProgressPercentage;
        @BindView(R.id.tv_expense_amount_number)
        TextView tvExpenseAmount;
        @BindView(R.id.img_enter)
        ImageView imgEnter;

        public RouteSegmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

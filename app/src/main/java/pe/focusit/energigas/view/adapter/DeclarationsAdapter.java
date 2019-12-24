package pe.focusit.energigas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import pe.focusit.energigas.R;
import pe.focusit.energigas.model.Declaration;
import pe.focusit.energigas.view.fragment.DeclarationStatusFragment;

public class DeclarationsAdapter extends RecyclerView.Adapter<DeclarationsAdapter.DeclarationViewHolder> {

    private int mStatus;
    private OnShowObservationListener mListener;
    private List<Declaration> mDeclarationList = new ArrayList<>();

    public DeclarationsAdapter(int status, @NonNull OnShowObservationListener listener) {
        mStatus = status;
        mListener = listener;
    }

    public void setData(List<Declaration> declarationList) {
        mDeclarationList = declarationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeclarationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_declaration_status, parent, false);
        return new DeclarationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeclarationViewHolder holder, int position) {
        Declaration declaration = mDeclarationList.get(position);
        Context context = holder.itemView.getContext();
        holder.tvRouteSegments.setText(declaration.getName());
        holder.tvRouteDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(declaration.getDateCreated()));
        switch (mStatus) {
            case DeclarationStatusFragment.STATUS_APPROVED:
                holder.imgDeclarationStatus.setImageDrawable(context.getDrawable(R.drawable.ic_circle_check));
                break;
            case DeclarationStatusFragment.STATUS_PENDING:
                holder.imgDeclarationStatus.setImageDrawable(context.getDrawable(R.drawable.ic_send));
                break;
            case DeclarationStatusFragment.STATUS_OBSERVED:
                holder.cvDeclaration.setOnClickListener(view -> mListener.onShowObservation(declaration));
                holder.imgDeclarationStatus.setImageDrawable(context.getDrawable(R.drawable.ic_eye));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDeclarationList == null ? 0 : mDeclarationList.size();
    }

    public interface OnShowObservationListener {
        void onShowObservation(Declaration observedDeclaration);
    }

    public class DeclarationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_declaration)
        MaterialCardView cvDeclaration;
        @BindView(R.id.tv_route_segments)
        TextView tvRouteSegments;
        @BindView(R.id.tv_route_date)
        TextView tvRouteDate;
        @BindView(R.id.img_declaration_status)
        ImageView imgDeclarationStatus;

        public DeclarationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

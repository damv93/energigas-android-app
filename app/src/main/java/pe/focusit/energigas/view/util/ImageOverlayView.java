package pe.focusit.energigas.view.util;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import pe.focusit.energigas.R;

public class ImageOverlayView extends ConstraintLayout {

    private String mPhotoFilePath;
    private OnCameraClickListener mOnCameraClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;

    public ImageOverlayView(Context context) {
        super(context);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPhotoFilePath(@NonNull String path) {
        mPhotoFilePath = path;
    }

    public void setOnCameraClick(@NonNull OnCameraClickListener listener) {
        mOnCameraClickListener = listener;
    }

    public void setOnDeleteClick(@NonNull OnDeleteClickListener listener) {
        mOnDeleteClickListener = listener;
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_image_overlay, this);
        view.findViewById(R.id.imgCamera).setOnClickListener(v -> {
            if (mOnCameraClickListener != null)
                mOnCameraClickListener.onCameraClick();
        });
        view.findViewById(R.id.imgDelete).setOnClickListener(v -> {
            if (mOnDeleteClickListener != null)
                mOnDeleteClickListener.onDeleteClick(mPhotoFilePath);
        });
        setBackgroundColor(Color.TRANSPARENT);
    }

    public interface OnCameraClickListener {
        void onCameraClick();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String photoFilePath);
    }
}

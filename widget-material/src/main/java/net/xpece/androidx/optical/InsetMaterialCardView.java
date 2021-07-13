package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

public class InsetMaterialCardView extends MaterialCardView implements OpticalInsetsView {

    private Insets mOpticalInsets = null;

    public InsetMaterialCardView(@NonNull Context context) {
        super(context);
    }

    public InsetMaterialCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetMaterialCardView(@NonNull Context context, @Nullable AttributeSet attrs,
                                 @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setUseCompatPadding(boolean useCompatPadding) {
        mOpticalInsets = null;
        super.setUseCompatPadding(useCompatPadding);
    }

    @Override
    public void setMaxCardElevation(float maxElevation) {
        mOpticalInsets = null;
        super.setMaxCardElevation(maxElevation);
    }

    @Override
    @NonNull
    public Insets getOpticalInsets() {
        if (mOpticalInsets == null) {
            if (Build.VERSION.SDK_INT < 21 || getUseCompatPadding()) {
                final float maxCardElevation = getMaxCardElevation();
                if (maxCardElevation != 0) {
                    mOpticalInsets = InsetsCompat.of(
                            (int) maxCardElevation,
                            (int) (maxCardElevation * 1.5f),
                            (int) maxCardElevation,
                            (int) (maxCardElevation * 1.5f)
                    );
                } else {
                    mOpticalInsets = InsetsCompat.NONE;
                }
            } else {
                mOpticalInsets = InsetsCompat.NONE;
            }
        }
        return mOpticalInsets;
    }

    @Deprecated
    @Override
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("InsetMaterialCardView", "Cannot set optical insets on this widget.");
    }
}

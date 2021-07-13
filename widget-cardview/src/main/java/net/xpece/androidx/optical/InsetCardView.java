package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class InsetCardView extends CardView implements OpticalInsetsView {

    private Insets mOpticalInsets = null;

    public InsetCardView(@NonNull Context context) {
        super(context);
    }

    public InsetCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetCardView(@NonNull Context context, @Nullable AttributeSet attrs,
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
            if (SDK_INT < 21 || getUseCompatPadding()) {
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
        Log.w("InsetCardView", "Cannot set optical insets on this widget.");
    }
}

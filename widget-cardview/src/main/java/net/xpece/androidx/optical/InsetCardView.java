package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;

public class InsetCardView extends CardView {

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

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
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

    //@Override
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("InsetCardView", "Cannot set optical insets on this widget.");
    }
}

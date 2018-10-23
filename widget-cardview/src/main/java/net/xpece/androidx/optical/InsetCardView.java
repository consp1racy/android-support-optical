package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

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
    Insets getOpticalInsets() {
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
}

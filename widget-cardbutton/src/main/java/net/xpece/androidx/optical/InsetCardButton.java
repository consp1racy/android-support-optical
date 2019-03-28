package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

import com.google.android.material.widget.CardButton;

public class InsetCardButton extends CardButton {

    private Insets mOpticalInsets = null;

    public InsetCardButton(@NonNull Context context) {
        super(context);
    }

    public InsetCardButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetCardButton(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(final ViewGroup.LayoutParams params) {
        final boolean autoVisualMarginEnabled = AUTO_VISUAL_MARGIN_ENABLED;
        try {
            AUTO_VISUAL_MARGIN_ENABLED = false;
            super.setLayoutParams(params);
        } finally {
            AUTO_VISUAL_MARGIN_ENABLED = autoVisualMarginEnabled;
        }
    }

    @Override
    public void setUseCompatPadding(boolean useCompatPadding) {
        mOpticalInsets = null;
        super.setUseCompatPadding(useCompatPadding);
    }

    @Override
    public void setPadding(final int left, final int top, final int right, final int bottom) {
        mOpticalInsets = null;
        super.setPadding(left, top, right, bottom);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Insets getOpticalInsets() {
        if (mOpticalInsets == null) {
            mOpticalInsets = InsetsCompat.of(
                    Math.max(getShadowPaddingLeft(), getContentInsetLeft()),
                    Math.max(getShadowPaddingTop(), getContentInsetTop()),
                    Math.max(getShadowPaddingRight(), getContentInsetRight()),
                    Math.max(getShadowPaddingBottom(), getContentInsetBottom())
            );
        }
        return mOpticalInsets;
    }

    //@Override
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("InsetCardButton", "Cannot set optical insets on this widget.");
    }
}

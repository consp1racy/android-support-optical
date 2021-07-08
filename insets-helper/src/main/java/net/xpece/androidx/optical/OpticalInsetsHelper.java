package net.xpece.androidx.optical;

import android.annotation.TargetApi;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * Helper class for making views aware of optical insets
 * on {@link android.graphics.drawable.InsetDrawable InsetDrawable} below API 21,
 * and {@link android.graphics.drawable.LayerDrawable LayerDrawable} (including children
 * such as {@link android.graphics.drawable.RippleDrawable RippleDrawable}) on all API levels.
 */
@RequiresApi(16)
public final class OpticalInsetsHelper {

    private final View view;

    private Insets opticalInsets = null;

    public OpticalInsetsHelper(final View view) {
        this.view = view;
    }

    /**
     * Return this from {@code View#getOpticalInsets()}.
     */
    @TargetApi(29)
    public Insets onGetOpticalInsets() {
        if (opticalInsets == null) {
            // Same as platform, we don't support changing insets once resolved.
            final Drawable background = view.getBackground();
            opticalInsets = background != null
                    ? OpticalInsets.getOpticalInsetsCompat(background)
                    : InsetsCompat.NONE;
            // If background.getOpticalInsets() returns null this will blow up.
        }
        return opticalInsets;
    }

    /**
     * Call this from {@code View#setOpticalInsets(Insets)}.
     */
    public void onSetOpticalInsets(final Insets insets) {
        if (opticalInsets != insets) {
            opticalInsets = insets;
            view.requestLayout();
        }
    }
}

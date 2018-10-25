package net.xpece.androidx.optical.sample;

import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import net.xpece.androidx.optical.InsetsCompat;

@RequiresApi(16)
public class OpticalInsetsHelper {

    private final View mView;

    private Insets mOpticalInsets = null;

    public OpticalInsetsHelper(final @NonNull View view) {
        this.mView = view;
    }

    @NonNull
    public Insets onGetOpticalInsets() {
        if (mOpticalInsets == null) {
            // Same as platform, we don't support changing insets once resolved.
            final Drawable background = mView.getBackground();
            if (background != null) {
                mOpticalInsets = OpticalInsets.getOpticalInsets(background);
            } else {
                mOpticalInsets = InsetsCompat.NONE;
            }
        }
        return mOpticalInsets;
    }
}

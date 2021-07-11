package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.NinePatchDrawable;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class DrawableInsets {

    @SuppressLint("NewApi")
    public static Insets getOpticalInsets(@Nullable Drawable d) {
        if (d == null) {
            return InsetsCompat.NONE;
        } else if (d instanceof InsetDrawable) {
            return InsetDrawableInsets.getOpticalInsets((InsetDrawable) d);
        } else if (d instanceof LayerDrawable) {
            return LayerDrawableInsets.getOpticalInsets((LayerDrawable) d);
        } else if (d instanceof NinePatchDrawable) {
            return NinePatchDrawableInsets.getOpticalInsets((NinePatchDrawable) d);
        } else {
            return d.getOpticalInsets();
        }
    }

    private DrawableInsets() {
    }
}

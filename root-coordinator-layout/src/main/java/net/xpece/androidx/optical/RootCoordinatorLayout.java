package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;

public class RootCoordinatorLayout extends CoordinatorLayout {
    public RootCoordinatorLayout(@NonNull Context context) {
        super(context);
    }

    public RootCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Insets getOpticalInsets() {
        return InsetsCompat.NONE;
    }

    //@Override
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("RootCoordinatorLayout", "Cannot set optical insets on this layout.");
    }
}

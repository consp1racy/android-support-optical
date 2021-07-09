package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Insets getOpticalInsets() {
        return InsetsCompat.NONE;
    }

    //@Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("RootCoordinatorLayout", "Cannot set optical insets on this layout.");
    }
}

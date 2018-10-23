package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class RootConstraintLayout extends ConstraintLayout {
    public RootConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public RootConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    Insets getOpticalInsets() {
        return InsetsCompat.NONE;
    }
}
package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;

public class RootFrameLayout extends FrameLayout {
    public RootFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RootFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(21)
    public RootFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        Log.w("RootFrameLayout", "Cannot set optical insets on this layout.");
    }
}

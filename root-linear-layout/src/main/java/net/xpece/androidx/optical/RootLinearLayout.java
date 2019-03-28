package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class RootLinearLayout extends LinearLayout {
    public RootLinearLayout(@NonNull Context context) {
        super(context);
    }

    public RootLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(21)
    public RootLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        Log.w("RootLinearLayout", "Cannot set optical insets on this layout.");
    }
}

package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import android.util.AttributeSet;
import android.util.Log;
import com.google.android.flexbox.FlexboxLayout;

public class RootFlexboxLayout extends FlexboxLayout {
    public RootFlexboxLayout(@NonNull Context context) {
        super(context);
    }

    public RootFlexboxLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootFlexboxLayout(@NonNull Context context, @Nullable AttributeSet attrs,
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
        Log.w("RootFlexboxLayout", "Cannot set optical insets on this layout.");
    }
}

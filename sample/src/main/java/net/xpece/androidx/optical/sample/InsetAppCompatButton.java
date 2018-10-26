package net.xpece.androidx.optical.sample;

import android.content.Context;
import android.graphics.Insets;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import net.xpece.androidx.optical.OpticalInsetsHelper;

public class InsetAppCompatButton extends AppCompatButton {

    private OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetAppCompatButton(final @NonNull Context context) {
        super(context);
    }

    public InsetAppCompatButton(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetAppCompatButton(final @NonNull Context context, final @Nullable AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    Insets getOpticalInsets() {
        return mOpticalHelper.onGetOpticalInsets();
    }
}

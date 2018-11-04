package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

import net.xpece.androidx.optical.OpticalInsetsHelper;

public class InsetAppCompatSpinner extends AppCompatSpinner {

    private OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetAppCompatSpinner(final @NonNull Context context) {
        super(context);
    }

    public InsetAppCompatSpinner(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetAppCompatSpinner(final @NonNull Context context, final @Nullable AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public Insets getOpticalInsets() {
        return mOpticalHelper.onGetOpticalInsets();
    }

    //@Override
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        mOpticalHelper.onSetOpticalInsets(insets);
    }
}

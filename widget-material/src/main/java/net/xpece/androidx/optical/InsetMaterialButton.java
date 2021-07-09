package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.google.android.material.button.MaterialButton;

public class InsetMaterialButton extends MaterialButton {

    private OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetMaterialButton(final @NonNull Context context) {
        super(context);
    }

    public InsetMaterialButton(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetMaterialButton(final @NonNull Context context, final @Nullable AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //@Override
    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public Insets getOpticalInsets() {
        return mOpticalHelper.onGetOpticalInsets();
    }

    //@Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @SuppressWarnings("unused")
    public void setOpticalInsets(@NonNull Insets insets) {
        mOpticalHelper.onSetOpticalInsets(insets);
    }
}

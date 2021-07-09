package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public class InsetButton extends Button {

    private OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetButton(final @NonNull Context context) {
        super(context);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetButton(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetButton(final @NonNull Context context, final @Nullable AttributeSet attrs,
                       final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOpticalHelper.onSetBackground(getBackground());
    }

    @Override
    public void setBackground(Drawable background) {
        if (mOpticalHelper != null) {
            mOpticalHelper.onSetBackground(background);
        }
        super.setBackground(background);
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

package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class InsetAppCompatEditText extends AppCompatEditText {

    private OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetAppCompatEditText(final @NonNull Context context) {
        super(context);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetAppCompatEditText(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetAppCompatEditText(final @NonNull Context context, final @Nullable AttributeSet attrs,
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

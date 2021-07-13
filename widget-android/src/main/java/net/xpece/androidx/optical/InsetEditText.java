package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InsetEditText extends EditText implements OpticalInsetsView {

    private final OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetEditText(final @NonNull Context context) {
        super(context);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetEditText(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
        mOpticalHelper.onSetBackground(getBackground());
    }

    public InsetEditText(final @NonNull Context context, final @Nullable AttributeSet attrs,
                         final @AttrRes int defStyleAttr) {
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

    @Override
    @NonNull
    public Insets getOpticalInsets() {
        return mOpticalHelper.onGetOpticalInsets();
    }

    @Override
    public void setOpticalInsets(@NonNull Insets insets) {
        mOpticalHelper.onSetOpticalInsets(insets);
    }
}

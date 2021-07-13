package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class InsetMaterialButton extends MaterialButton implements OpticalInsetsView {

    private final OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);

    public InsetMaterialButton(final @NonNull Context context) {
        super(context);
    }

    public InsetMaterialButton(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetMaterialButton(final @NonNull Context context, final @Nullable AttributeSet attrs,
                               final @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

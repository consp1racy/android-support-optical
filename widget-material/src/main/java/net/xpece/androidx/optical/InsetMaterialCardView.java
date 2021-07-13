package net.xpece.androidx.optical;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

public class InsetMaterialCardView extends MaterialCardView implements OpticalInsetsView {

    private final CardViewHelper mCardViewHelper = new CardViewHelper(this);

    public InsetMaterialCardView(@NonNull Context context) {
        super(context);
    }

    public InsetMaterialCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InsetMaterialCardView(@NonNull Context context, @Nullable AttributeSet attrs,
                                 @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setUseCompatPadding(boolean useCompatPadding) {
        mCardViewHelper.onSetUseCompatPadding();
        super.setUseCompatPadding(useCompatPadding);
    }

    @Override
    public void setMaxCardElevation(float maxElevation) {
        mCardViewHelper.onSetMaxCardElevation();
        super.setMaxCardElevation(maxElevation);
    }

    @Override
    @NonNull
    public Insets getOpticalInsets() {
        return mCardViewHelper.onGetOpticalInsets();
    }

    @Deprecated
    @Override
    public void setOpticalInsets(@NonNull Insets insets) {
        Log.w("InsetMaterialCardView", "Cannot set optical insets on this widget.");
    }
}

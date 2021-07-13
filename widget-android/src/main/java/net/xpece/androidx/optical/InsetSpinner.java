package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Spinner;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InsetSpinner extends Spinner implements OpticalInsetsView, SpinnerHelper.Delegate {

    private final OpticalInsetsHelper mOpticalHelper = new OpticalInsetsHelper(this);
    private final SpinnerHelper<InsetSpinner> mSpinnerHelper = new SpinnerHelper<>(this);

    public InsetSpinner(final @NonNull Context context) {
        super(context);
        mOpticalHelper.onSetBackground(getBackground());
        mSpinnerHelper.init(null, android.R.attr.spinnerStyle);
    }

    public InsetSpinner(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
        mOpticalHelper.onSetBackground(getBackground());
        mSpinnerHelper.init(attrs, android.R.attr.spinnerStyle);
    }

    public InsetSpinner(final @NonNull Context context, final @Nullable AttributeSet attrs,
                        final @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOpticalHelper.onSetBackground(getBackground());
        mSpinnerHelper.init(attrs, defStyleAttr);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mSpinnerHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("WrongCall")
    @Override
    public void superOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void doSetMeasuredDimension(int measuredWidth, int measuredHeight) {
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mSpinnerHelper.onLayout(changed, l, t, r, b);
    }

    @SuppressLint("WrongCall")
    @Override
    public void superOnLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @SuppressWarnings("unused")
    boolean isLayoutModeOptical() {
        return mSpinnerHelper.isLayoutModeOptical();
    }
}

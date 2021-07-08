package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;
import static android.view.ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@SuppressLint("NewApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class SpinnerHelper<T extends Spinner & SpinnerHelper.Delegate> {

    private final T mSpinner;

    private boolean matchParentLayoutMode = false;

    public SpinnerHelper(T spinner) {
        this.mSpinner = spinner;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (SDK_INT < 18) {
            onMeasureDefault(widthMeasureSpec, heightMeasureSpec);
        } else {
            onMeasureSpecial(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @SuppressLint("WrongCall")
    private void onMeasureDefault(int widthMeasureSpec, int heightMeasureSpec) {
        mSpinner.superOnMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @RequiresApi(18)
    private void onMeasureSpecial(int widthMeasureSpec, int heightMeasureSpec) {
        matchParentLayoutMode = true;
        try {
            mSpinner.superOnMeasure(widthMeasureSpec, heightMeasureSpec);

            if (getLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS) {
                final Insets insets = mSpinner.getOpticalInsets();
                int adjustWidth = -(insets.left + insets.right);
                int adjustHeight = -(insets.top + insets.bottom);
                mSpinner.doSetMeasuredDimension(mSpinner.getMeasuredWidth() + adjustWidth,
                        mSpinner.getMeasuredHeight() + adjustHeight);
            }
        } finally {
            matchParentLayoutMode = false;
        }
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (SDK_INT < 18 || getLayoutMode() != LAYOUT_MODE_OPTICAL_BOUNDS) {
            onLayoutInClipBoundsMode(changed, l, t, r, b);
        } else {
            onLayoutInOpticalBoundsMode(changed, l, t, r, b);
        }
    }

    private void onLayoutInClipBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
    }

    @RequiresApi(18)
    private void onLayoutInOpticalBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
        final View v = mSpinner.getChildAt(0);
        if (v != null) {
            final Insets insets = mSpinner.getOpticalInsets();
            v.offsetLeftAndRight(-insets.left);
        }
    }

    @RequiresApi(18)
    public boolean isLayoutModeOptical() {
        if (matchParentLayoutMode) {
            return getParentLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS;
        } else {
            return getLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS;
        }
    }

    @RequiresApi(18)
    private int getParentLayoutMode() {
        return ((ViewGroup) mSpinner.getParent()).getLayoutMode();
    }

    @RequiresApi(18)
    private int getLayoutMode() {
        return mSpinner.getLayoutMode();
    }

    public interface Delegate {

        Insets getOpticalInsets();

        void superOnLayout(boolean changed, int l, int t, int r, int b);

        void superOnMeasure(int widthMeasureSpec, int heightMeasureSpec);

        void doSetMeasuredDimension(int measuredWidth, int measuredHeight);
    }
}

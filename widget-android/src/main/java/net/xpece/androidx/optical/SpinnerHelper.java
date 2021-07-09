package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;
import static android.view.ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@SuppressLint("NewApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class SpinnerHelper<T extends Spinner & SpinnerHelper.Delegate> {

    private static final int [] ATTRS = new int[] {
        android.R.attr.clipToPadding
    };

    private final T mSpinner;

    private boolean matchParentLayoutMode = false;

    public SpinnerHelper(T spinner) {
        this.mSpinner = spinner;
    }

    public void init(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        final TypedArray t = mSpinner.getContext()
                .obtainStyledAttributes(attrs, ATTRS, defStyleAttr, 0);
        try {
            if (!t.hasValue(0)) {
                mSpinner.setClipToPadding(false);
            }
        } finally {
            t.recycle();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (SDK_INT < 18) {
            mSpinner.superOnMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            onMeasureSpecial(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @RequiresApi(18)
    private void onMeasureSpecial(int widthMeasureSpec, int heightMeasureSpec) {
        matchParentLayoutMode = true;
        try {
            mSpinner.superOnMeasure(widthMeasureSpec, heightMeasureSpec);

            if (shouldCompensateOpticalBoundsLayoutMode()) {
                compensateOpticalBoundsLayoutMode();
            } else if (shouldCompensateClipBoundsLayoutMode()) {
                compensateClipBoundsLayoutMode();
            }
        } finally {
            matchParentLayoutMode = false;
        }
    }

    private boolean shouldCompensateOpticalBoundsLayoutMode() {
        final int layoutMode = getLayoutMode();
        return layoutMode == LAYOUT_MODE_OPTICAL_BOUNDS && layoutMode == getParentLayoutMode();
    }

    private void compensateOpticalBoundsLayoutMode() {
        Insets insets = mSpinner.getOpticalInsets();
        int adjustWidth = -(insets.left + insets.right);
        int adjustHeight = -(insets.top + insets.bottom);
        final View v = mSpinner.getChildAt(0);
        if (v != null) {
            insets = getOpticalInsetsCompat(v);
            adjustWidth -= insets.left + insets.right;
            adjustHeight -= insets.top + insets.bottom;
        }
        resetMeasuredDimension(adjustWidth, adjustHeight);
    }

    private Insets getOpticalInsetsCompat(@NonNull View v) {
        Insets insets;
        try {
            insets = OpticalInsets.getOpticalInsets(v);
        } catch (Throwable ignore) {
            insets = OpticalInsets.getOpticalInsetsCompat(v.getBackground());
        }
        return insets;
    }

    private void resetMeasuredDimension(int adjustWidth, int adjustHeight) {
        mSpinner.doSetMeasuredDimension(mSpinner.getMeasuredWidth() + adjustWidth,
                mSpinner.getMeasuredHeight() + adjustHeight);
    }

    private boolean shouldCompensateClipBoundsLayoutMode() {
        final int layoutMode = getLayoutMode();
        return layoutMode != LAYOUT_MODE_OPTICAL_BOUNDS && layoutMode != getParentLayoutMode();
    }

    private void compensateClipBoundsLayoutMode() {
        final Insets insets = mSpinner.getOpticalInsets();
        int adjustWidth = -(insets.left + insets.right);
        int adjustHeight = -(insets.top + insets.bottom);
        if (SDK_INT >= 21) {
            adjustWidth = -adjustWidth;
            adjustHeight = -adjustHeight * 2;
        }
        resetMeasuredDimension(adjustWidth, adjustHeight);
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (SDK_INT < 18) {
            mSpinner.superOnLayout(changed, l, t, r, b);
        } else {
            onLayoutSpecial(changed, l, t, r, b);
        }
    }

    @RequiresApi(18)
    private void onLayoutSpecial(boolean changed, int l, int t, int r, int b) {
        if (getLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS) {
            onLayoutInOpticalBoundsMode(changed, l, t, r, b);
        } else {
            onLayoutInClipBoundsMode(changed, l, t, r, b);
        }
    }

    @RequiresApi(18)
    private void onLayoutInOpticalBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
        final View v = mSpinner.getChildAt(0);
        if (v != null) {
            Insets insets = mSpinner.getOpticalInsets();
            v.offsetLeftAndRight(-insets.left);
            if (getLayoutMode() != getParentLayoutMode()) {
                v.offsetTopAndBottom(-insets.top);
            }
        }
    }

    @RequiresApi(18)
    private void onLayoutInClipBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
        if (getLayoutMode() != getParentLayoutMode()) {
            final View v = mSpinner.getChildAt(0);
            if (v != null) {
                final Insets insets = mSpinner.getOpticalInsets();
                v.offsetTopAndBottom(insets.top);
            }
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

package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;
import static android.view.View.MeasureSpec;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@SuppressLint("NewApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class SpinnerHelper<T extends Spinner & SpinnerHelper.Delegate> {

    private static final int[] ATTRS = new int[]{
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
        matchParentLayoutMode = true;
        try {
            mSpinner.superOnMeasure(widthMeasureSpec, heightMeasureSpec);

            if (shouldCompensateOpticalBoundsLayoutMode()) {
                compensateOpticalBoundsLayoutMode(widthMeasureSpec, heightMeasureSpec);
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

    private void compensateOpticalBoundsLayoutMode(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == EXACTLY && heightMode == EXACTLY) {
            return;
        }

        int adjustWidth = 0;
        int adjustHeight = 0;

        final Insets insets = mSpinner.getOpticalInsets();
        if (widthMode != EXACTLY) {
            adjustWidth -= insets.left + insets.right;
        }
        if (heightMode != EXACTLY) {
            adjustHeight -= insets.top + insets.bottom;
        }

        View v = mSpinner.getSelectedView();
        if (v == null) {
            final SpinnerAdapter adapter = mSpinner.getAdapter();
            final int index = mSpinner.getSelectedItemPosition();
            if (adapter != null && index >= 0) {
                v = adapter.getView(index, null, mSpinner);
            }
        }
        if (v != null) {
            final Insets childInsets = getOpticalInsetsCompat(v);
            if (widthMode != EXACTLY) {
                adjustWidth -= childInsets.left + childInsets.right;
            }
            if (heightMode != EXACTLY) {
                adjustHeight -= childInsets.top + childInsets.bottom;
            }
        }

        resetMeasuredDimension(adjustWidth, adjustHeight);

        // Fix child dimensions with MATCH_PARENT width/height.
        if (widthMode == EXACTLY) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(widthMeasureSpec) + insets.left + insets.right,
                    EXACTLY);
        }
        if (heightMode == EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(heightMeasureSpec) + insets.top + insets.bottom,
                    EXACTLY);
        }
        AbsSpinnerReflection.setMeasureSpecs(mSpinner, widthMeasureSpec, heightMeasureSpec);
    }

    private Insets getOpticalInsetsCompat(@NonNull View v) {
        Insets insets;
        try {
            insets = ViewInsets.getOpticalInsets(v);
        } catch (NoSuchMethodException ignore) {
            insets = DrawableInsets.getOpticalInsets(v.getBackground());
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
            adjustHeight = -adjustHeight;
        }
        resetMeasuredDimension(adjustWidth, adjustHeight);
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS) {
            onLayoutInOpticalBoundsMode(changed, l, t, r, b);
        } else {
            onLayoutInClipBoundsMode(changed, l, t, r, b);
        }
    }

    private void onLayoutInOpticalBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
        final View v = mSpinner.getSelectedView();
        if (v != null) {
            final Insets insets = mSpinner.getOpticalInsets();
            v.offsetLeftAndRight(-insets.left);
            if (getLayoutMode() != getParentLayoutMode()) {
                v.offsetTopAndBottom(-insets.top);
            }
        }
    }

    private void onLayoutInClipBoundsMode(boolean changed, int l, int t, int r, int b) {
        mSpinner.superOnLayout(changed, l, t, r, b);
        if (getLayoutMode() != getParentLayoutMode()) {
            final View v = mSpinner.getSelectedView();
            if (v != null) {
                final Insets insets = mSpinner.getOpticalInsets();
                v.offsetTopAndBottom(insets.top);
            }
        }
    }

    public boolean isLayoutModeOptical() {
        if (matchParentLayoutMode) {
            return getParentLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS;
        } else {
            return getLayoutMode() == LAYOUT_MODE_OPTICAL_BOUNDS;
        }
    }

    private int getParentLayoutMode() {
        return ((ViewGroup) mSpinner.getParent()).getLayoutMode();
    }

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

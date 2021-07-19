package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.AbsSpinner;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint("SoonBlockedPrivateApi")
final class AbsSpinnerReflection {

    private static final boolean REFLECTION_RESOLVED;
    private static final Field FIELD_WIDTH_MEASURE_SPEC;
    private static final Field FIELD_HEIGHT_MEASURE_SPEC;

    static {
        boolean reflectionResolved = false;
        Field widthMeasureSpec = null;
        Field heightMeasureSpec = null;
        try {
            widthMeasureSpec = AbsSpinner.class
                    .getDeclaredField("mWidthMeasureSpec");
            widthMeasureSpec.setAccessible(true);
            heightMeasureSpec = AbsSpinner.class
                    .getDeclaredField("mHeightMeasureSpec");
            heightMeasureSpec.setAccessible(true);
            reflectionResolved = true;
        } catch (NoSuchFieldException e) {
            Log.w("AbsSpinnerReflection", e.toString());
        }
        REFLECTION_RESOLVED = reflectionResolved;
        FIELD_WIDTH_MEASURE_SPEC = widthMeasureSpec;
        FIELD_HEIGHT_MEASURE_SPEC = heightMeasureSpec;
    }

    /**
     * This only works on API < 28.
     */
    public static void setMeasureSpecs(
            @NonNull AbsSpinner spinner,
            int widthMeasureSpec,
            int heightMeasureSpec) {
        if (REFLECTION_RESOLVED) {
            try {
                FIELD_WIDTH_MEASURE_SPEC.set(spinner, widthMeasureSpec);
                FIELD_HEIGHT_MEASURE_SPEC.set(spinner, heightMeasureSpec);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AbsSpinnerReflection() {
    }
}

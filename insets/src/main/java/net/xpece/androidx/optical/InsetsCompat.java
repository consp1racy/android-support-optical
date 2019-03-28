package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

import java.lang.reflect.Method;

/**
 * An Insets instance holds four integer offsets which describe changes to the four
 * edges of a Rectangle. By convention, positive values move edges towards the
 * centre of the rectangle.
 * <p>
 * Insets are immutable so may be treated as values.
 */
@RequiresApi(16)
@SuppressLint("PrivateApi")
@SuppressWarnings("unused")
public final class InsetsCompat {

    @NonNull
    public static final Insets NONE;

    // Insets were on the dark greylist during Android Pie Developer Preview.
    // It looks they're on the light greylist in public release.
    private static boolean sSafe = !"P".equals(Build.VERSION.CODENAME);

    static {
        Insets none;
        try {
            none = of(0, 0, 0, 0);
        } catch (NoSuchMethodError ex) {
            sSafe = false;
            none = of(0, 0, 0, 0);
        }
        NONE = none;
    }

    private InsetsCompat() {
    }

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param left   the left inset
     * @param top    the top inset
     * @param right  the right inset
     * @param bottom the bottom inset
     * @return Insets instance with the appropriate values
     */
    public static Insets of(int left, int top, int right, int bottom) {
        if (sSafe) {
            return Insets.of(left, top, right, bottom);
        } else {
            final InsetDrawable insets = new InsetDrawable(null, left, top, right, bottom);
            return Reflection.getOpticalBounds(insets);
        }
    }

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param r the rectangle from which to take the values
     * @return an Insets instance with the appropriate values
     */
    public static Insets of(@Nullable Rect r) {
        if (r == null) {
            return NONE;
        } else {
            return of(r.left, r.top, r.right, r.bottom);
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    static Insets union(final @NonNull Insets... insets) {
        int left = 0, top = 0, right = 0, bottom = 0;
        for (final Insets i : insets) {
            left = Math.max(left, i.left);
            top = Math.max(top, i.top);
            right = Math.max(right, i.right);
            bottom = Math.max(bottom, i.bottom);
        }
        return of(left, top, right, bottom);
    }

    // Having separate class prevents reflective method lookup when it's not needed.
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static class Reflection {

        private static Method METHOD_GET_OPTICAL_INSETS;

        static {
            try {
                METHOD_GET_OPTICAL_INSETS = Drawable.class.getDeclaredMethod("getOpticalInsets");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        private Reflection() {
        }

        @NonNull
        static Insets getOpticalBounds(final @NonNull Drawable drawable) {
            try {
                return (Insets) METHOD_GET_OPTICAL_INSETS.invoke(drawable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

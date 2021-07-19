package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class BitmapInsets {

    private static Method sMethodGetLayoutBounds;

    @SuppressLint("NewApi")
    static int[] getLayoutBounds(@NonNull Bitmap b) throws NoSuchMethodException {
        if (sMethodGetLayoutBounds == null) {
            sMethodGetLayoutBounds = Bitmap.class
                    .getDeclaredMethod("getLayoutBounds");
        }

        try {
            return (int[]) sMethodGetLayoutBounds.invoke(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("NewApi")
    public static Insets getOpticalInsets(@NonNull Bitmap b) throws NoSuchMethodException {
        final int[] layoutBounds = getLayoutBounds(b);
        if (layoutBounds != null) {
            return InsetsCompat.of(
                    layoutBounds[0],
                    layoutBounds[1],
                    layoutBounds[2],
                    layoutBounds[3]
            );
        } else {
            return InsetsCompat.NONE;
        }
    }

    private BitmapInsets() {
    }
}

package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint("SoonBlockedPrivateApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class ViewInsets {

    private static Method sMethodGetOpticalInsets;

    public static Insets getOpticalInsets(@NonNull View v) throws NoSuchMethodException {
        if (v instanceof OpticalInsetsView) {
            return ((OpticalInsetsView) v).getOpticalInsets();
        } else {
            return getOpticalInsetsNative(v);
        }
    }

    @SuppressLint("NewApi")
    @Nullable
    private static Insets getOpticalInsetsNative(@NonNull View v) throws NoSuchMethodException {
        if (sMethodGetOpticalInsets == null) {
            sMethodGetOpticalInsets = View.class
                    .getDeclaredMethod("getOpticalInsets");
        }

        try {
            return (Insets) sMethodGetOpticalInsets.invoke(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private ViewInsets() {
    }
}

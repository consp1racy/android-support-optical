package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.drawable.NinePatchDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressLint("NewApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class NinePatchDrawableInsets {

    private static final DrawableInsets.Delegate<NinePatchDrawable> IMPL;

    static {
        if (SDK_INT >= 21) {
            IMPL = DrawableInsets.Delegate.getDefault();
        } else {
            IMPL = new Api18();
        }
    }

    public static Insets getOpticalInsets(@Nullable NinePatchDrawable d) {
        if (d != null) {
            return IMPL.getOpticalInsets(d);
        } else {
            return InsetsCompat.NONE;
        }
    }

    private NinePatchDrawableInsets() {
    }

    @SuppressWarnings({"rawtypes", "JavaReflectionMemberAccess"})
    @SuppressLint({"SoonBlockedPrivateApi", "DiscouragedPrivateApi", "PrivateApi"})
    private static final class Api18 implements DrawableInsets.Delegate<NinePatchDrawable> {

        private static final boolean REFLECTION_RESOLVED;
        private static final Field FIELD_NINE_PATCH;
        private static final Field FIELD_NINE_PATCH_STATE;
        private static final Method METHOD_COMPUTE_BITMAP_SIZE;

        private static final Method METHOD_GET_BITMAP;
        private static final Field FIELD_OPTICAL_INSETS;

        static {
            boolean reflectionResolved = false;
            Field fieldNinePatch = null;
            Field fieldNinePatchState = null;
            Method methodComputeBitmapSize = null;
            Method methodGetBitmap = null;
            Field fieldOpticalInsets = null;
            try {
                fieldNinePatch = NinePatchDrawable.class
                        .getDeclaredField("mNinePatch");
                fieldNinePatch.setAccessible(true);
                fieldNinePatchState = NinePatchDrawable.class
                        .getDeclaredField("mNinePatchState");
                fieldNinePatchState.setAccessible(true);
                methodComputeBitmapSize = NinePatchDrawable.class
                        .getDeclaredMethod("computeBitmapSize");
                methodComputeBitmapSize.setAccessible(true);

                final Class classNinePatchState =
                        Class.forName("android.graphics.drawable.NinePatchDrawable$NinePatchState");
                methodGetBitmap = classNinePatchState.getDeclaredMethod("getBitmap");
                methodGetBitmap.setAccessible(true);
                fieldOpticalInsets = classNinePatchState.getDeclaredField("mOpticalInsets");
                fieldOpticalInsets.setAccessible(true);

                reflectionResolved = true;
            } catch (NoSuchFieldException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace(); // TODO
            }
            REFLECTION_RESOLVED = reflectionResolved;
            FIELD_NINE_PATCH = fieldNinePatch;
            FIELD_NINE_PATCH_STATE = fieldNinePatchState;
            METHOD_COMPUTE_BITMAP_SIZE = methodComputeBitmapSize;
            METHOD_GET_BITMAP = methodGetBitmap;
            FIELD_OPTICAL_INSETS = fieldOpticalInsets;
        }

        private static boolean hasNinePatch(@NonNull NinePatchDrawable d) {
            try {
                return FIELD_NINE_PATCH.get(d) != null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @NonNull
        private static Object getNinePatchState(@NonNull NinePatchDrawable d) {
            try {
                return FIELD_NINE_PATCH_STATE.get(d);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @NonNull
        private static Bitmap getBitmap(@NonNull Object state) {
            try {
                return (Bitmap) METHOD_GET_BITMAP.invoke(state);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
            }
        }

        private static void setOpticalInsets(@NonNull Object state, @NonNull Insets insets) {
            try {
                FIELD_OPTICAL_INSETS.set(state, insets);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        private static void computeBitmapSize(@NonNull NinePatchDrawable d) {
            try {
                METHOD_COMPUTE_BITMAP_SIZE.invoke(d);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
            }
        }

        private Api18() {
        }

        @NonNull
        @Override
        public Insets getOpticalInsets(@NonNull NinePatchDrawable d) {
            if (REFLECTION_RESOLVED) {
                return getOpticalInsetsImpl(d);
            } else {
                return InsetsCompat.NONE;
            }
        }

        @NonNull
        private Insets getOpticalInsetsImpl(@NonNull NinePatchDrawable d) {
            Insets insets = d.getOpticalInsets();
            if (InsetsCompat.NONE.equals(insets) && hasNinePatch(d)) {
                Object state = getNinePatchState(d);
                final Bitmap bitmap = getBitmap(state);
                try {
                    insets = BitmapInsets.getOpticalInsets(bitmap);
                } catch (NoSuchMethodException ignore) {
                }
                if (insets != InsetsCompat.NONE) {
                    d.mutate();
                    state = getNinePatchState(d);
                    setOpticalInsets(state, insets);
                    computeBitmapSize(d);
                }
            }
            return insets;
        }
    }
}

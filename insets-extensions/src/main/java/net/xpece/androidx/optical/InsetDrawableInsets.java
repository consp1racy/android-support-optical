package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.drawable.InsetDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

@SuppressLint("NewApi")
final class InsetDrawableInsets {

    private static final DrawableInsets.Delegate<InsetDrawable> IMPL;

    static {
        if (SDK_INT >= 21) {
            IMPL = DrawableInsets.Delegate.getDefault();
        } else {
            IMPL = new Api18();
        }
    }

    public static Insets getOpticalInsets(@Nullable InsetDrawable d) {
        if (d != null) {
            return IMPL.getOpticalInsets(d);
        } else {
            return InsetsCompat.NONE;
        }
    }

    private InsetDrawableInsets() {
    }

    @SuppressWarnings({"rawtypes", "JavaReflectionMemberAccess"})
    @SuppressLint({"DiscouragedPrivateApi", "PrivateApi", "SoonBlockedPrivateApi"})
    private static final class Api18 implements DrawableInsets.Delegate<InsetDrawable> {

        private static final boolean REFLECTION_RESOLVED;
        private static final Field FIELD_INSET_STATE;

        private static final Field FIELD_INSET_LEFT;
        private static final Field FIELD_INSET_TOP;
        private static final Field FIELD_INSET_RIGHT;
        private static final Field FIELD_INSET_BOTTOM;

        static {
            boolean reflectionResolved = false;
            Field fieldInsetState = null;
            Field fieldInsetLeft = null;
            Field fieldInsetTop = null;
            Field fieldInsetRight = null;
            Field fieldInsetBottom = null;
            try {
                fieldInsetState = InsetDrawable.class.getDeclaredField("mInsetState");
                fieldInsetState.setAccessible(true);

                final Class classInsetState =
                        Class.forName("android.graphics.drawable.InsetDrawable$InsetState");
                fieldInsetLeft = classInsetState.getDeclaredField("mInsetLeft");
                fieldInsetLeft.setAccessible(true);
                fieldInsetTop = classInsetState.getDeclaredField("mInsetTop");
                fieldInsetTop.setAccessible(true);
                fieldInsetRight = classInsetState.getDeclaredField("mInsetRight");
                fieldInsetRight.setAccessible(true);
                fieldInsetBottom = classInsetState.getDeclaredField("mInsetBottom");
                fieldInsetBottom.setAccessible(true);

                reflectionResolved = true;
            } catch (NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace(); // TODO
            }
            REFLECTION_RESOLVED = reflectionResolved;
            FIELD_INSET_STATE = fieldInsetState;
            FIELD_INSET_LEFT = fieldInsetLeft;
            FIELD_INSET_TOP = fieldInsetTop;
            FIELD_INSET_RIGHT = fieldInsetRight;
            FIELD_INSET_BOTTOM = fieldInsetBottom;
        }

        private Api18() {
        }

        @NonNull
        @Override
        public Insets getOpticalInsets(@NonNull InsetDrawable d) {
            if (REFLECTION_RESOLVED) {
                return getOpticalInsetsImpl(d);
            } else {
                return d.getOpticalInsets();
            }
        }

        private Insets getOpticalInsetsImpl(InsetDrawable d) {
            try {
                final Object state = FIELD_INSET_STATE.get(d);
                return InsetsCompat.of(
                        FIELD_INSET_LEFT.getInt(state),
                        FIELD_INSET_TOP.getInt(state),
                        FIELD_INSET_RIGHT.getInt(state),
                        FIELD_INSET_BOTTOM.getInt(state)
                );
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // TODO
                return d.getOpticalInsets();
            }
        }
    }
}

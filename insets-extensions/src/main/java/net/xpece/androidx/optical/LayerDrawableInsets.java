package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.drawable.LayerDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.util.Arrays;

@SuppressLint("NewApi")
final class LayerDrawableInsets {

    private static final Delegate IMPL;

    static {
        if (SDK_INT >= 23) {
            IMPL = new Api23();
        } else {
            IMPL = new Api18();
        }
    }

    public static Insets getOpticalInsets(@Nullable LayerDrawable d) {
        if (d != null) {
            return IMPL.getOpticalInsets(d);
        } else {
            return InsetsCompat.NONE;
        }
    }

    private LayerDrawableInsets() {
    }

    private interface Delegate {

        @NonNull
        Insets getOpticalInsets(@NonNull LayerDrawable d);
    }

    private abstract static class BaseDelegate implements Delegate {

        @NonNull
        @Override
        public Insets getOpticalInsets(@NonNull LayerDrawable d) {
            Insets insets = d.getOpticalInsets();
            if (InsetsCompat.NONE.equals(insets)) {
                final Insets[] allInsets = new Insets[d.getNumberOfLayers()];
                getLayerOpticalInsets(d, allInsets);
                insets = InsetsCompat.union(allInsets);
            }
            return insets;
        }

        abstract void getLayerOpticalInsets(@NonNull LayerDrawable d, @NonNull Insets[] out);
    }

    @SuppressWarnings({"rawtypes", "JavaReflectionMemberAccess"})
    @SuppressLint({"DiscouragedPrivateApi", "PrivateApi", "SoonBlockedPrivateApi"})
    private static class Api18 extends BaseDelegate {

        private static final boolean REFLECTION_RESOLVED;
        private static final Field FIELD_LAYER_STATE;
        private static final Field FIELD_CHILDREN;

        private static final Field FIELD_INSET_LEFT;
        private static final Field FIELD_INSET_TOP;
        private static final Field FIELD_INSET_RIGHT;
        private static final Field FIELD_INSET_BOTTOM;

        static {
            boolean reflectionResolved = false;
            Field fieldLayerState = null;
            Field fieldChildren = null;
            Field fieldInsetLeft = null;
            Field fieldInsetTop = null;
            Field fieldInsetRight = null;
            Field fieldInsetBottom = null;
            try {
                fieldLayerState = LayerDrawable.class.getDeclaredField("mLayerState");
                fieldLayerState.setAccessible(true);

                fieldChildren = Class.forName("android.graphics.drawable.LayerDrawable$LayerState")
                        .getDeclaredField("mChildren");
                fieldChildren.setAccessible(true);

                final Class classChildDrawable =
                        Class.forName("android.graphics.drawable.LayerDrawable$ChildDrawable");
                fieldInsetLeft = classChildDrawable.getDeclaredField("mInsetL");
                fieldInsetLeft.setAccessible(true);
                fieldInsetTop = classChildDrawable.getDeclaredField("mInsetT");
                fieldInsetTop.setAccessible(true);
                fieldInsetRight = classChildDrawable.getDeclaredField("mInsetR");
                fieldInsetRight.setAccessible(true);
                fieldInsetBottom = classChildDrawable.getDeclaredField("mInsetB");
                fieldInsetBottom.setAccessible(true);

                reflectionResolved = true;
            } catch (NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace(); // TODO
            }
            REFLECTION_RESOLVED = reflectionResolved;
            FIELD_LAYER_STATE = fieldLayerState;
            FIELD_CHILDREN = fieldChildren;
            FIELD_INSET_LEFT = fieldInsetLeft;
            FIELD_INSET_TOP = fieldInsetTop;
            FIELD_INSET_RIGHT = fieldInsetRight;
            FIELD_INSET_BOTTOM = fieldInsetBottom;
        }

        private Api18() {
        }

        @NonNull
        @Override
        public Insets getOpticalInsets(@NonNull LayerDrawable d) {
            if (REFLECTION_RESOLVED) {
                return super.getOpticalInsets(d);
            } else {
                return d.getOpticalInsets();
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        void getLayerOpticalInsets(@NonNull LayerDrawable d, @NonNull Insets[] out) {
            try {
                final Object state = FIELD_LAYER_STATE.get(d);
                final Object[] children = (Object[]) FIELD_CHILDREN.get(state);
                for (int i = 0, size = out.length; i < size; i++) {
                    final Object child = children[i];
                    final Insets layerInsets = DrawableInsets.getOpticalInsets(d.getDrawable(i));
                    out[i] = InsetsCompat.of(
                            FIELD_INSET_LEFT.getInt(child) + layerInsets.left,
                            FIELD_INSET_TOP.getInt(child) + layerInsets.top,
                            FIELD_INSET_RIGHT.getInt(child) + layerInsets.right,
                            FIELD_INSET_BOTTOM.getInt(child) + layerInsets.bottom
                    );
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // TODO
                Arrays.fill(out, InsetsCompat.NONE);
            }
        }
    }

    @RequiresApi(23)
    private static class Api23 extends BaseDelegate {

        private Api23() {
        }

        @Override
        void getLayerOpticalInsets(@NonNull LayerDrawable d, @NonNull Insets[] out) {
            for (int i = 0, size = out.length; i < size; i++) {
                final Insets layerInsets = DrawableInsets.getOpticalInsets(d.getDrawable(i));
                out[i] = InsetsCompat.of(
                        d.getLayerInsetLeft(i) + layerInsets.left,
                        d.getLayerInsetTop(i) + layerInsets.top,
                        d.getLayerInsetRight(i) + layerInsets.right,
                        d.getLayerInsetBottom(i) + layerInsets.bottom
                );
            }
        }
    }
}

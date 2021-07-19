package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class DrawableContainerInsets {

    private static final Delegate IMPL;

    static {
        if (SDK_INT >= 21) {
            IMPL = new Api21();
        } else {
            IMPL = new Api18();
        }
    }

    public static void fixNinePatchInsets(@Nullable DrawableContainer d) {
        if (d != null) {
            IMPL.fixNinePatchInsets(d);
        }
    }

    private interface Delegate {

        void fixNinePatchInsets(@NonNull DrawableContainer d);
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    @SuppressLint("SoonBlockedPrivateApi")
    private static class Api18 implements Delegate {

        private static final Field FIELD_CUR_INDEX;
        private static final boolean REFLECTION_RESOLVED;

        static {
            Field fieldCurIndex = null;
            boolean reflectionResolved = false;
            try {
                fieldCurIndex = DrawableContainer.class
                        .getDeclaredField("mCurIndex");
                fieldCurIndex.setAccessible(true);

                reflectionResolved = true;
            } catch (Exception e) {
                Log.w("DrawableContainerInsets", e.toString());
            }
            FIELD_CUR_INDEX = fieldCurIndex;
            REFLECTION_RESOLVED = reflectionResolved;
        }

        private static int getCurrentIndex(@NonNull DrawableContainer d) {
            try {
                return FIELD_CUR_INDEX.getInt(d);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void fixNinePatchInsets(@NonNull DrawableContainer d) {
            if (REFLECTION_RESOLVED) {
                fixNinePatchInsetsImpl(d);
            }
        }

        private void fixNinePatchInsetsImpl(@NonNull DrawableContainer d) {
            final List<Drawable> drawables = flatten(d);
            for (Drawable child : drawables) {
                if (child instanceof NinePatchDrawable) {
                    NinePatchDrawableInsets.getOpticalInsets((NinePatchDrawable) child);
                }
            }
            Collections.reverse(drawables);
            for (Drawable child : drawables) {
                if (child instanceof DrawableContainer) {
                    reselectCurrentDrawable((DrawableContainer) child);
                }
            }
        }

        private static List<Drawable> flatten(@NonNull DrawableContainer input) {
            final List<Drawable> output = new ArrayList<>();
            int start = 0;
            output.add(input);
            while (start < output.size()) {
                Drawable d;
                DrawableContainerIterator it;
                final int end = output.size();
                for (int i = start; i < end; i++) {
                    d = output.get(i);
                    if (d instanceof DrawableContainer) {
                        it = new DrawableContainerIterator((DrawableContainer) d);
                        while (it.hasNext()) {
                            Drawable child = it.next();
                            output.add(child);
                        }
                    }
                }
                start = end;
            }
            return output;
        }

        private static void reselectCurrentDrawable(@NonNull DrawableContainer d) {
            final int index = getCurrentIndex(d);
            if (index >= 0) {
                d.selectDrawable(-1);
                d.selectDrawable(index);
            }
        }
    }

    private static class Api21 implements Delegate {

        @Override
        public void fixNinePatchInsets(@NonNull DrawableContainer d) {
            // No-op.
        }
    }
}

package net.xpece.androidx.optical;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.Iterator;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint({"DiscouragedPrivateApi", "LongLogTag"})
final class DrawableContainerIterator implements Iterator<Drawable> {

    private static final Drawable[] EMPTY_ARRAY = new Drawable[0];

    private static final Field FIELD_STATE;
    private static final Field FIELD_DRAWABLES;
    private static final boolean REFLECTION_RESOLVED;

    static {
        Field fieldState = null;
        Field fieldDrawables = null;
        boolean reflectionResolved = false;
        try {
            fieldState = DrawableContainer.class
                    .getDeclaredField("mDrawableContainerState");
            fieldState.setAccessible(true);

            final Class<?> classState =
                    Class.forName("android.graphics.drawable.DrawableContainer$DrawableContainerState");
            fieldDrawables = classState
                    .getDeclaredField("mDrawables");
            fieldDrawables.setAccessible(true);

            reflectionResolved = true;
        } catch (Exception e) {
            Log.w("DrawableContainerIterator", e.toString());
        }
        FIELD_STATE = fieldState;
        FIELD_DRAWABLES = fieldDrawables;
        REFLECTION_RESOLVED = reflectionResolved;
    }

    @Nullable
    private static Drawable[] getDrawables(@NonNull DrawableContainer drawable) {
        Drawable[] drawables = EMPTY_ARRAY;
        if (REFLECTION_RESOLVED) {
            try {
                final Object state = FIELD_STATE.get(drawable);
                drawables = (Drawable[]) FIELD_DRAWABLES.get(state);
            } catch (Exception e) {
                e.printStackTrace(); // TODO
            }
        }
        return drawables;
    }

    private final Drawable[] drawables;

    private int position = -1;

    public DrawableContainerIterator(@NonNull DrawableContainer drawable) {
        drawables = getDrawables(drawable);
    }

    @Override
    public boolean hasNext() {
        position++;
        while (position < drawables.length && drawables[position] == null) {
            position++;
        }
        return position < drawables.length;
    }

    @Override
    public Drawable next() {
        if (position < drawables.length) {
            return drawables[position];
        } else {
            return null;
        }
    }
}

package net.xpece.androidx.optical;

import androidx.annotation.Nullable;
import com.google.android.material.card.MaterialCardView;
import androidx.cardview.widget.CardView;
import android.view.View;

public class InsetViewInflaters {

    private static Boolean sReplaceCardViews = false;

    /**
     * Controls whether fully-qualified {@link CardView}s in XML layouts should be inflated to their
     * inset-aware counterparts automatically.
     *
     * @param replaceCardViews Replace fully-qualified {@link CardView}s in XML layouts
     *                         with their inset-aware counterparts.
     */
    public static void setReplaceCardViews(final boolean replaceCardViews) {
        sReplaceCardViews = replaceCardViews;
    }

    static boolean isReplaceCardViews() {
        return sReplaceCardViews;
    }

    @Nullable
    static final String CARD_VIEW_CLASS_NAME;

    @Nullable
    static final String MATERIAL_CARD_VIEW_CLASS_NAME;

    static {
        // TODO Consumer proguard rules: dontwarn, dontnote missing classes.
        CARD_VIEW_CLASS_NAME = getParentClassName(CardView.class);
        MATERIAL_CARD_VIEW_CLASS_NAME = getParentClassName(MaterialCardView.class);
    }

    @Nullable
    static String getParentClassName(Class<? extends View> klazz) {
        String name = null;
        try {
            //noinspection ConstantConditions
            name = klazz.getSuperclass().getName();
        } catch (Throwable ignore) {
        }
        return name;
    }

    private InsetViewInflaters() {
    }
}

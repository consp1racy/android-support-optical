package net.xpece.androidx.optical.sample;

import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.CardView;
import android.view.View;

public class InsetViewInflaters {

    private static Boolean sTranslateCardViewFqcns = false;

    /**
     * Controls whether fully-qualified {@link CardView}s in XML layouts should be inflated to their
     * inset-aware counterparts automatically.
     *
     * @param replaceCardViews Replace fully-qualified {@link CardView}s in XML layouts
     *                         with their inset-aware counterparts.
     */
    public static void setReplaceCardViews(final boolean replaceCardViews) {
        sTranslateCardViewFqcns = replaceCardViews;
    }

    static boolean isReplaceCardViews() {
        return sTranslateCardViewFqcns;
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
    private static String getParentClassName(Class<? extends View> klazz) {
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

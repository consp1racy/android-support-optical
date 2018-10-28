package net.xpece.androidx.optical.sample;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.theme.MaterialComponentsViewInflater;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;

import net.xpece.androidx.optical.InsetCardView;
import net.xpece.androidx.optical.InsetMaterialCardView;

@Keep
@SuppressWarnings("unused")
public class InsetMaterialComponentsViewInflater extends MaterialComponentsViewInflater {

    @NonNull
    @Override
    protected AppCompatButton createButton(final @NonNull Context context,
            final @NonNull AttributeSet attrs) {
        return new InsetMaterialButton(context, attrs);
    }

    @NonNull
    @Override
    protected AppCompatEditText createEditText(final @NonNull Context context,
            final @NonNull AttributeSet attrs) {
        return new InsetAppCompatEditText(context, attrs);
    }

    @NonNull
    @Override
    protected AppCompatSpinner createSpinner(final @NonNull Context context,
            final @NonNull AttributeSet attrs) {
        return new InsetAppCompatSpinner(context, attrs);
    }

    @Nullable
    @Override
    protected View createView(final @NonNull Context context, final @NonNull String name,
            final @NonNull AttributeSet attrs) {
        if (InsetViewInflaters.isReplaceCardViews() &&
                name.equals(InsetViewInflaters.CARD_VIEW_CLASS_NAME)) {
            return new InsetCardView(context, attrs);
        } else if (InsetViewInflaters.isReplaceCardViews() &&
                name.equals(InsetViewInflaters.MATERIAL_CARD_VIEW_CLASS_NAME)) {
            return new InsetMaterialCardView(context, attrs);
        } else {
            return super.createView(context, name, attrs);
        }
    }
}

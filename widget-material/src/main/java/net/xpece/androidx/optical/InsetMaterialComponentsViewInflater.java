package net.xpece.androidx.optical;

import android.content.Context;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.theme.MaterialComponentsViewInflater;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

@Keep
@SuppressWarnings("unused")
public class InsetMaterialComponentsViewInflater extends MaterialComponentsViewInflater {

    private static final String TAG = "InsetViewInflater";

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
            try {
                return new InsetCardView(context, attrs);
            } catch (LinkageError ex) {
                Log.e(TAG,
                        "Couldn't inflate InsetCardView automatically. Did you declare dependency"
                                + " on widget-cardview?");
            }
        } else if (InsetViewInflaters.isReplaceCardViews() &&
                name.equals(InsetViewInflaters.MATERIAL_CARD_VIEW_CLASS_NAME)) {
            return new InsetMaterialCardView(context, attrs);
        }
        return super.createView(context, name, attrs);
    }
}

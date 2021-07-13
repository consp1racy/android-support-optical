package net.xpece.androidx.optical;

import static android.os.Build.VERSION.SDK_INT;

import android.graphics.Insets;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.cardview.widget.CardView;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class CardViewHelper {

    private final CardView mCardView;

    private Insets mOpticalInsets = null;

    public CardViewHelper(@NonNull CardView cardView) {
        this.mCardView = cardView;
    }

    public Insets onGetOpticalInsets() {
        if (mOpticalInsets == null) {
            if (SDK_INT < 21 || mCardView.getUseCompatPadding()) {
                final float maxCardElevation = mCardView.getMaxCardElevation();
                if (maxCardElevation != 0) {
                    mOpticalInsets = InsetsCompat.of(
                            (int) maxCardElevation,
                            (int) (maxCardElevation * 1.5f),
                            (int) maxCardElevation,
                            (int) (maxCardElevation * 1.5f)
                    );
                } else {
                    mOpticalInsets = InsetsCompat.NONE;
                }
            } else {
                mOpticalInsets = InsetsCompat.NONE;
            }
        }
        return mOpticalInsets;
    }

    public void onSetUseCompatPadding() {
        mOpticalInsets = null;
    }

    public void onSetMaxCardElevation() {
        mOpticalInsets = null;
    }
}

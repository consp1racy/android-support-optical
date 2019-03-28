package net.xpece.androidx.optical

import android.graphics.Insets
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.RequiresApi
import android.view.View

/**
 * Helper class for making views aware of optical insets on [InsetDrawable] below API 21,
 * and [LayerDrawable] (including children such as [RippleDrawable]) on all API levels.
 */
@RequiresApi(16)
class OpticalInsetsHelper(private val view: View) {

    private var opticalInsets: Insets? = null

    /**
     * Return this from `View.getOpticalInsets()`.
     */
    fun onGetOpticalInsets(): Insets {
        if (opticalInsets == null) {
            // Same as platform, we don't support changing insets once resolved.
            opticalInsets = view.background?.getOpticalInsets() ?: InsetsCompat.NONE
        }
        return opticalInsets!!
    }

    /**
     * Call this from `View.setOpticalInsets(Insets)`.
     */
    fun onSetOpticalInsets(insets: Insets) {
        if (opticalInsets != insets) {
            opticalInsets = insets
            view.requestLayout()
        }
    }
}

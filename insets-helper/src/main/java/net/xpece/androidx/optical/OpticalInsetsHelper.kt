package net.xpece.androidx.optical

import android.graphics.Insets
import android.graphics.drawable.InsetDrawable
import android.view.View

/**
 * Helper class for making views aware of optical insets on [InsetDrawable] below API 21.
 *
 * This class is not needed on API 21 and above.
 */
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
}

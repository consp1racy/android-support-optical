package net.xpece.androidx.optical

import android.app.Activity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

// SDK 15 and older is not supported in Robolectric.
@RunWith(RobolectricTestRunner::class)
class InsetCardViewTest {

    @Config(sdk = [19])
    @Test
    fun getOpticalInsets_returnsMaxCardElevation_onAndroid4() {
        val activity = Robolectric.setupActivity(Activity::class.java)
        val card = InsetCardView(activity)
        card.layout(0, 0, 100, 100)
        // padding - contentPadding is not good enough. It's compound of two rounded numbers.
        val expected = with(card) {
            InsetsCompat.of(
                maxCardElevation.toInt(),
                (maxCardElevation * 1.5f).toInt(),
                maxCardElevation.toInt(),
                (maxCardElevation * 1.5f).toInt()
            )
        }
        assertEquals(expected, card.opticalInsets)
    }

    @Config(sdk = [21])
    @Test
    fun getOpticalInsets_returnsMaxCardElevation_onLollipopWithCompatPaddingEnabled() {
        val activity = Robolectric.setupActivity(Activity::class.java)
        val card = InsetCardView(activity)
        card.useCompatPadding = true
        card.layout(0, 0, 100, 100)
        // padding - contentPadding is not good enough. It's compound of two rounded numbers.
        val expected = with(card) {
            InsetsCompat.of(
                maxCardElevation.toInt(),
                (maxCardElevation * 1.5f).toInt(),
                maxCardElevation.toInt(),
                (maxCardElevation * 1.5f).toInt()
            )
        }
        assertEquals(expected, card.opticalInsets)
    }

    @Config(sdk = [21])
    @Test
    fun getOpticalInsets_returnsNone_onLollipopWithCompatPaddingDisabled() {
        val activity = Robolectric.setupActivity(Activity::class.java)
        val card = InsetCardView(activity)
        card.useCompatPadding = false
        card.layout(0, 0, 100, 100)
        assertEquals(InsetsCompat.NONE, card.opticalInsets)
    }
}

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
@Config(sdk = [18])
class RootCoordinatorLayoutTest {

    @Test
    fun getOpticalInsets_returnsNone() {
        val activity = Robolectric.setupActivity(Activity::class.java)
        val layout = RootCoordinatorLayout(activity)
        assertEquals(InsetsCompat.NONE, layout.opticalInsets)
    }
}

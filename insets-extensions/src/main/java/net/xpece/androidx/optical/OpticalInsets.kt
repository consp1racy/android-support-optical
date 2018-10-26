@file:JvmName("OpticalInsets")
@file:Suppress("unused")

package net.xpece.androidx.optical

import android.annotation.SuppressLint
import android.graphics.Insets
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import kotlin.LazyThreadSafetyMode.NONE

private val viewGetter by lazy(NONE) {
    View::class.java.getDeclaredMethod("getOpticalInsets")
}

/**
 * Returns the insets that will be used during optical bounds layout mode.
 */
@RequiresApi(16)
fun View.getOpticalInsets(): Insets = viewGetter.invoke(this) as Insets

private val drawableGetter by lazy(NONE) {
    Drawable::class.java.getDeclaredMethod("getOpticalInsets")
}

private val drawableLegacyGetter by lazy(NONE) {
    Drawable::class.java.getDeclaredMethod("getLayoutInsets")
}

// This condition check may be optimized away by R8 depending on consumer's min SDK version.
private fun Drawable.getActualOpticalInsets(): Insets = if (Build.VERSION.SDK_INT < 18) {
    drawableLegacyGetter.invoke(this) as Insets
} else {
    drawableGetter.invoke(this) as Insets
}

/**
 * Returns the layout insets suggested by this Drawable for use with alignment
 * operations during layout.
 */
@RequiresApi(16)
@SuppressLint("LogNotTimber")
fun Drawable.getOpticalInsets(): Insets = if (Build.VERSION.SDK_INT < 21 && this is InsetDrawable) {
    val actual = getActualOpticalInsets()
    if (actual === Insets.NONE) {
        // If the value is the same instance as the framework's NONE
        // it most certainly means a custom value wasn't provided by a potential subclass.
        try {
            InsetDrawableReflection.getOpticalInsets(this)
        } catch (ex: Throwable) {
            Log.w(
                "OpticalInsets",
                "Couldn't access InsetDrawable data using reflection. Oh well... ${ex.localizedMessage}"
            )
            actual
        }
    } else {
        // If the value is different from framework's NONE,
        // it probably means it was overridden using InsetsCompat.
        actual
    }
} else if (this is LayerDrawable) {
    val actual = getActualOpticalInsets()
    if (actual === Insets.NONE) {
        val allInsets = (0 until numberOfLayers).map { i ->
            val d = getDrawable(i)
            val drawableInsets = d.getOpticalInsets()
            val layerInsets = getLayerInsets(i)
            InsetsCompat.plus(drawableInsets, layerInsets)
        }
        InsetsCompat.maxOf(*allInsets.toTypedArray())
    } else {
        actual
    }
} else {
    getActualOpticalInsets()
}

private fun LayerDrawable.getLayerInsets(i: Int): Insets = if (Build.VERSION.SDK_INT >= 23) {
    InsetsCompat.of(
        getLayerInsetLeft(i),
        getLayerInsetTop(i),
        getLayerInsetRight(i),
        getLayerInsetBottom(i)
    )
} else {
    try {
        LayerDrawableReflection.getLayerInsets(this, i)
    } catch (ex: Throwable) {
        Log.w(
            "OpticalInsets",
            "Couldn't access LayerDrawable data using reflection. Oh well... ${ex.localizedMessage}"
        )
        InsetsCompat.NONE
    }
}

/**
 * This class is only used below Marshmallow.
 */
private object LayerDrawableReflection {

    private val fieldLayerState = LayerDrawable::class.java.getDeclaredField("mLayerState")
        .apply { isAccessible = true }

    @SuppressLint("PrivateApi")
    private val fieldChildren =
        Class.forName("android.graphics.drawable.LayerDrawable\$LayerState")
            .getDeclaredField("mChildren")
            .apply { isAccessible = true }

    @SuppressLint("PrivateApi")
    private val classChildDrawable =
        Class.forName("android.graphics.drawable.LayerDrawable\$ChildDrawable")

    private val fieldInsetLeft = classChildDrawable.getDeclaredField("mInsetL")
        .apply { isAccessible = true }
    private val fieldInsetTop = classChildDrawable.getDeclaredField("mInsetT")
        .apply { isAccessible = true }
    private val fieldInsetRight = classChildDrawable.getDeclaredField("mInsetR")
        .apply { isAccessible = true }
    private val fieldInsetBottom = classChildDrawable.getDeclaredField("mInsetB")
        .apply { isAccessible = true }

    @RequiresApi(16)
    internal fun getLayerInsets(drawable: LayerDrawable, index: Int): Insets {
        val state = fieldLayerState.get(drawable)
        val children = fieldChildren.get(state) as Array<*>
        val layer = children[index]
        return InsetsCompat.of(
            fieldInsetLeft.getInt(layer),
            fieldInsetTop.getInt(layer),
            fieldInsetRight.getInt(layer),
            fieldInsetBottom.getInt(layer)
        )
    }
}

/**
 * This class is only used below Lollipop.
 */
private object InsetDrawableReflection {

    private val fieldInsetState = InsetDrawable::class.java.getDeclaredField("mInsetState")
        .apply { isAccessible = true }

    @SuppressLint("PrivateApi")
    private val classInsetState =
        Class.forName("android.graphics.drawable.InsetDrawable\$InsetState")

    private val fieldInsetLeft = classInsetState.getDeclaredField("mInsetLeft")
        .apply { isAccessible = true }
    private val fieldInsetTop = classInsetState.getDeclaredField("mInsetTop")
        .apply { isAccessible = true }
    private val fieldInsetRight = classInsetState.getDeclaredField("mInsetRight")
        .apply { isAccessible = true }
    private val fieldInsetBottom = classInsetState.getDeclaredField("mInsetBottom")
        .apply { isAccessible = true }

    /**
     * Extracts insets from an [InsetDrawable].
     */
    @RequiresApi(16)
    internal fun getOpticalInsets(drawable: InsetDrawable): Insets {
        val state = fieldInsetState.get(drawable)
        return InsetsCompat.of(
            fieldInsetLeft.getInt(state),
            fieldInsetTop.getInt(state),
            fieldInsetRight.getInt(state),
            fieldInsetBottom.getInt(state)
        )
    }
}
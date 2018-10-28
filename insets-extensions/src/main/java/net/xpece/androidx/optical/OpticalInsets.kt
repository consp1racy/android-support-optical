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
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.LazyThreadSafetyMode.NONE

private val viewInsetsGetter by lazy(NONE) {
    View::class.java.getDeclaredMethod("getOpticalInsets")
}

/**
 * Returns the insets that will be used during optical bounds layout mode.
 */
@RequiresApi(16)
fun View.getOpticalInsets(): Insets = viewInsetsGetter.invoke(this) as Insets

private val drawableInsetsGetter by lazy(NONE) {
    // This condition check may be optimized away by R8 depending on consumer's min SDK version.
    if (Build.VERSION.SDK_INT < 18) {
        Drawable::class.java.getDeclaredMethod("getLayoutInsets")
    } else {
        Drawable::class.java.getDeclaredMethod("getOpticalInsets")
    }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Drawable.getActualOpticalInsets(): Insets =
    drawableInsetsGetter.invoke(this) as Insets

private val isLoggedInsetDrawableReflectionError = AtomicBoolean(false)
private val isLoggedLayerDrawableReflectionError = AtomicBoolean(false)

/**
 * Returns the layout insets suggested by this Drawable for use with alignment
 * operations during layout.
 */
@RequiresApi(16)
fun Drawable.getOpticalInsets(): Insets = if (Build.VERSION.SDK_INT < 21 && this is InsetDrawable) {
    val actual = getActualOpticalInsets()
    if (actual == InsetsCompat.NONE) {
        try {
            InsetDrawableReflection.getOpticalInsets(this)
        } catch (ex: Throwable) {
            if (!isLoggedInsetDrawableReflectionError.getAndSet(true)) {
                Log.w(
                    "OpticalInsets",
                    "Couldn't access InsetDrawable data using reflection. Oh well...",
                    ex
                )
            }
            actual
        }
    } else {
        actual
    }
} else if (this is LayerDrawable) {
    val actual = getActualOpticalInsets()
    if (actual == InsetsCompat.NONE) {
        val allInsets = Array(numberOfLayers, this::getTotalLayerInsets)
        InsetsCompat.union(*allInsets)
    } else {
        actual
    }
} else {
    getActualOpticalInsets()
}

private fun LayerDrawable.getTotalLayerInsets(i: Int): Insets = if (Build.VERSION.SDK_INT >= 23) {
    val drawableInsets = getDrawable(i).getOpticalInsets()
    InsetsCompat.of(
        getLayerInsetLeft(i) + drawableInsets.left,
        getLayerInsetTop(i) + drawableInsets.top,
        getLayerInsetRight(i) + drawableInsets.right,
        getLayerInsetBottom(i) + drawableInsets.bottom
    )
} else {
    try {
        LayerDrawableReflection.getTotalLayerInsets(this, i)
    } catch (ex: Throwable) {
        if (!isLoggedLayerDrawableReflectionError.getAndSet(true)) {
            Log.w(
                "OpticalInsets",
                "Couldn't access LayerDrawable data using reflection. Oh well...",
                ex
            )
        }
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
    internal fun getTotalLayerInsets(drawable: LayerDrawable, i: Int): Insets {
        val drawableInsets = drawable.getDrawable(i).getOpticalInsets()
        val state = fieldLayerState.get(drawable)
        val children = fieldChildren.get(state) as Array<*>
        val layer = children[i]
        return InsetsCompat.of(
            fieldInsetLeft.getInt(layer) + drawableInsets.left,
            fieldInsetTop.getInt(layer) + drawableInsets.top,
            fieldInsetRight.getInt(layer) + drawableInsets.right,
            fieldInsetBottom.getInt(layer) + drawableInsets.bottom
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
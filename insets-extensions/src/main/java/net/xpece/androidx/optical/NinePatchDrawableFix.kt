@file:JvmName("NinePatchDrawableFix")

package net.xpece.androidx.optical

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Insets
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.NinePatchDrawable
import android.os.Build.VERSION.SDK_INT

@JvmName("fixInsets")
@SuppressLint("RestrictedApi")
fun Drawable.fixNinePatchInsets() {
    if (SDK_INT >= 21) return

    when (this) {
        is DrawableContainer -> {
            flatten()
                .filterIsInstance<NinePatchDrawable>()
                .forEach { it.fixInsets() }
            selectDrawable(-1)
            selectDrawable(DrawableContainerReflection.getCurrentIndex(this))
        }
        is NinePatchDrawable -> {
            fixInsets()
        }
    }
}

private fun DrawableContainer.flatten(): Sequence<Drawable> {
    return drawables().flatMap {
        when (it) {
            is DrawableContainer -> flatten()
            else -> sequenceOf(it)
        }
    }
}

@SuppressLint("DiscouragedPrivateApi")
private fun DrawableContainer.drawables(): Sequence<Drawable> {
    val state = DrawableContainerReflection.getState(this)
    val drawables = DrawableContainerReflection.getDrawables(state)
    return drawables.asSequence().filterNotNull()
}

@SuppressLint("SoonBlockedPrivateApi", "DiscouragedPrivateApi")
private object DrawableContainerReflection {

    private val drawableContainerState = DrawableContainer::class.java
        .getDeclaredField("mDrawableContainerState")
        .apply { isAccessible = true }

    fun getState(drawable: DrawableContainer): Any {
        return drawableContainerState.get(drawable) as Any
    }

    private val drawableContainerStateClass =
        Class.forName("android.graphics.drawable.DrawableContainer\$DrawableContainerState")

    private val drawables = drawableContainerStateClass
        .getDeclaredField("mDrawables")
        .apply { isAccessible = true }

    fun getDrawables(state: Any): Array<Drawable?> {
        return drawables.get(state) as Array<Drawable?>
    }

    private val curIndex = DrawableContainer::class.java
        .getDeclaredField("mCurIndex")
        .apply { isAccessible = true }

    fun getCurrentIndex(drawable: DrawableContainer): Int {
        return curIndex.getInt(drawable)
    }
}

@SuppressLint("NewApi")
private fun NinePatchDrawable.fixInsets() {
    if (InsetsCompat.NONE == opticalInsets && NinePatchReflection.hasNinePatch(this)) {
        val state = NinePatchReflection.getState(this)
        val bitmap = NinePatchReflection.getBitmap(state)
        val insets = NinePatchReflection.getOpticalInsets(bitmap)
        NinePatchReflection.setOpticalInsets(state, insets)
        NinePatchReflection.computeBitmapSize(this)
    }
}

@SuppressLint("SoonBlockedPrivateApi", "DiscouragedPrivateApi", "PrivateApi")
private object NinePatchReflection {

    private val ninePatch = NinePatchDrawable::class.java
        .getDeclaredField("mNinePatch")
        .apply { isAccessible = true }

    fun hasNinePatch(drawable: NinePatchDrawable): Boolean {
        return ninePatch.get(drawable) != null
    }

    private val ninePatchState = NinePatchDrawable::class.java
        .getDeclaredField("mNinePatchState")
        .apply { isAccessible = true }

    fun getState(drawable: NinePatchDrawable): Any {
        return ninePatchState.get(drawable) as Any
    }

    private val ninePatchStateClass =
        Class.forName("android.graphics.drawable.NinePatchDrawable\$NinePatchState")

    private val getBitmap = ninePatchStateClass
        .getDeclaredMethod("getBitmap")
        .apply { isAccessible = true }

    fun getBitmap(state: Any): Bitmap {
        return getBitmap.invoke(state) as Bitmap
    }

    private val getLayoutBounds = Bitmap::class.java
        .getDeclaredMethod("getLayoutBounds")
        .apply { isAccessible = true }

    fun getOpticalInsets(bitmap: Bitmap): Insets {
        // https://github.com/aosp-mirror/platform_frameworks_base/blob/c46c4a6765196bcabf3ea89771a1f9067b22baad/graphics/java/android/graphics/drawable/Drawable.java#L847
        return (getLayoutBounds.invoke(bitmap) as IntArray?)
            ?.let { InsetsCompat.of(it[0], it[1], it[2], it[3]) }
            ?: InsetsCompat.NONE
    }

    private val opticalInsets = ninePatchStateClass
        .getDeclaredField("mOpticalInsets")
        .apply { isAccessible = true }

    fun setOpticalInsets(state: Any, insets: Insets) {
        // https://github.com/aosp-mirror/platform_frameworks_base/blob/c46c4a6765196bcabf3ea89771a1f9067b22baad/graphics/java/android/graphics/drawable/NinePatchDrawable.java#L370
        opticalInsets.set(state, insets)
    }

    private val computeBitmapSize = NinePatchDrawable::class.java
        .getDeclaredMethod("computeBitmapSize")
        .apply { isAccessible = true }

    fun computeBitmapSize(drawable: NinePatchDrawable) {
        // https://github.com/aosp-mirror/platform_frameworks_base/blob/c46c4a6765196bcabf3ea89771a1f9067b22baad/graphics/java/android/graphics/drawable/NinePatchDrawable.java#L194
        computeBitmapSize.invoke(drawable)
    }
}

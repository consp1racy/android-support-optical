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

    try {
        when (this) {
            is DrawableContainer -> {
                val drawables = flatten().toList()
                drawables.asSequence()
                    .filterIsInstance<NinePatchDrawable>()
                    .forEach { NinePatchDrawableInsets.getOpticalInsets(it) }
                drawables.asSequence()
                    .filterIsInstance<DrawableContainer>()
                    .forEach {
                        val i = DrawableContainerReflection.getCurrentIndex(it)
                        it.selectDrawable(-1)
                        it.selectDrawable(i)
                    }
            }
            is NinePatchDrawable -> {
                NinePatchDrawableInsets.getOpticalInsets(this)
            }
        }
    } catch (ex: Throwable) {
        ex.printStackTrace() // TODO
    }
}

private fun DrawableContainer.flatten(): Sequence<Drawable> {
    return sequenceOf(this) + drawables().flatMap {
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

    @Suppress("UNCHECKED_CAST")
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

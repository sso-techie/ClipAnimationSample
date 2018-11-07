package com.ahnstory.customanimationsample.animation

import android.animation.ValueAnimator
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.widget.ImageView
import com.ahnstory.customanimationsample.util.Size
import com.ahnstory.customanimationsample.util.div
import com.ahnstory.customanimationsample.util.getSize
import com.ahnstory.customanimationsample.widget.CroppedImageView

/**
 * Created by SSO on 2018. 11. 7..
 * If you have a question, please send an e-mail (sso.techie@gmail.com)
 */
class ZoomAnimationController(private val view: CroppedImageView, startRect: Rect, private val viewRect: Rect, imageSize: Size) {
    companion object {
        const val DURATION = 300L
    }

    private val startViewRect: RectF
    private val scale: Float

    private val startClipRect: RectF
    private val animatingRect: Rect
    private var cropAnimation: ValueAnimator? = null

    init {
        val startImageRect = getProportionalRect(startRect, imageSize, ImageView.ScaleType.CENTER_CROP)
        startViewRect = getProportionalRect(startImageRect, viewRect.getSize(), ImageView.ScaleType.CENTER_CROP)
        scale = startViewRect.width() / viewRect.width()

        val finalImageRect = getProportionalRect(viewRect, imageSize, ImageView.ScaleType.FIT_CENTER)
        startClipRect = getProportionalRect(finalImageRect, startRect.getSize() / scale, ImageView.ScaleType.FIT_CENTER)
        animatingRect = Rect()
        startClipRect.round(animatingRect)

        Log.d("SSO", "startRect=$startRect")
        Log.d("SSO", "viewRect=$viewRect")
        Log.d("SSO", "imageSize=$imageSize")
        Log.d("SSO", "startImageRect=$startImageRect")
        Log.d("SSO", "startViewRect=$startViewRect")
        Log.d("SSO", "finalImageRect=$finalImageRect")
        Log.d("SSO", "startClipRect=$startClipRect")
    }

    fun init() {
        view.x = startViewRect.left
        view.y = startViewRect.top
        view.pivotX = 0f
        view.pivotY = 0f
        view.scaleX = scale
        view.scaleY = scale

        view.setClipRegion(animatingRect)
    }

    fun startAnimation() {
        cropAnimation = createCropAnimator().apply {
            start()
        }
        view.animate()
                .x(0f)
                .y(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(DURATION)
                .start()
    }

    private fun createCropAnimator(): ValueAnimator {
        return ValueAnimator.ofFloat(0f, 1f).apply {
            duration = DURATION
            addUpdateListener {
                val weight = animatedValue as Float
                animatingRect.set(
                        (startClipRect.left * (1 - weight) + viewRect.left * weight).toInt(),
                        (startClipRect.top * (1 - weight) + viewRect.top * weight).toInt(),
                        (startClipRect.right * (1 - weight) + viewRect.right * weight).toInt(),
                        (startClipRect.bottom * (1 - weight) + viewRect.bottom * weight).toInt()
                )
                Log.d("SSO", "animatingRect=$animatingRect")
                view.setClipRegion(animatingRect)
            }
        }
    }

    private fun getProportionalRect(viewRect: Rect, imageSize: Size, scaleType: ImageView.ScaleType): RectF {
        return getProportionalRect(RectF(viewRect), imageSize, scaleType)
    }

    private fun getProportionalRect(viewRect: RectF, imageSize: Size, scaleType: ImageView.ScaleType): RectF {
        val viewRatio = viewRect.height() / viewRect.width()
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            return if (viewRatio > imageSize.ratio) {
                val width = viewRect.width()
                val height = width * imageSize.ratio
                val paddingY = (height - viewRect.height()) / 2f
                RectF(viewRect.left, viewRect.top - paddingY, viewRect.right, viewRect.bottom + paddingY)
            } else {
                val height = viewRect.height()
                val width = height / imageSize.ratio
                val paddingX = (width - viewRect.width()) / 2f
                RectF(viewRect.left - paddingX, viewRect.top, viewRect.right + paddingX, viewRect.bottom)
            }
        } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            return if (viewRatio < imageSize.ratio) {
                val width = viewRect.width()
                val height = width * imageSize.ratio
                val paddingY = (height - viewRect.height()) / 2f
                RectF(viewRect.left, viewRect.top - paddingY, viewRect.right, viewRect.bottom + paddingY)
            } else {
                val height = viewRect.height()
                val width = height / imageSize.ratio
                val paddingX = (width - viewRect.width()) / 2f
                RectF(viewRect.left - paddingX, viewRect.top, viewRect.right + paddingX, viewRect.bottom)
            }
        }

        return RectF()
    }

    fun cancel() {
        cropAnimation?.cancel()
        cropAnimation = null
        view.animate().cancel()
    }
}

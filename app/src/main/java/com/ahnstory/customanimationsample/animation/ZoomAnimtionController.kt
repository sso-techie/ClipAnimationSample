package com.ahnstory.customanimationsample.animation

import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.widget.ImageView
import com.ahnstory.customanimationsample.util.Size
import com.ahnstory.customanimationsample.util.getSize
import com.ahnstory.customanimationsample.widget.CroppedImageView

/**
 * Created by SSO on 2018. 11. 7..
 * If you have a question, please send an e-mail (sso.techie@gmail.com)
 */
class ZoomAnimtionController(private val view: CroppedImageView, startRect: Rect, viewRect: Rect, imageSize: Size) {
    private val startViewRect: RectF
    private val scale: Float

    init {
        val startImageRect = getProportionalRect(startRect, imageSize, ImageView.ScaleType.CENTER_CROP)
        startViewRect = getProportionalRect(startImageRect, viewRect.getSize(), ImageView.ScaleType.CENTER_CROP)
        scale = startViewRect.width() / viewRect.width()

        Log.d("SSO", "startRect=$startRect")
        Log.d("SSO", "viewRect=$viewRect")
        Log.d("SSO", "imageSize=$imageSize")
        Log.d("SSO", "startImageRect=$startImageRect")
        Log.d("SSO", "startViewRect=$startViewRect")
    }

    fun init() {
        view.x = startViewRect.left
        view.y = startViewRect.top
        view.pivotX = 0f
        view.pivotY = 0f
        view.scaleX = scale
        view.scaleY = scale
    }

    private fun getProportionalRect(viewRect: Rect, imageSize: Size, scaleType: ImageView.ScaleType): RectF {
        return getProportionalRect(RectF(viewRect), imageSize, scaleType)
    }

    private fun getProportionalRect(viewRect: RectF, imageSize: Size, scaleType: ImageView.ScaleType): RectF {
        val viewRatio = viewRect.height() / viewRect.width()
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            if (viewRatio > imageSize.ratio) {
                val width = viewRect.width()
                val height = width * imageSize.ratio
                val paddingY = (viewRect.height() - height) / 2f
                return RectF(viewRect.left, viewRect.top - paddingY, viewRect.right, viewRect.bottom + paddingY)
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
}
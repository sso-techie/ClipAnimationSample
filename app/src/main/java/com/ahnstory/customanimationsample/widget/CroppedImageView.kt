package com.ahnstory.customanimationsample.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by SSO on 2018. 11. 7..
 * If you have a question, please send an e-mail (sso.techie@gmail.com)
 */
class CroppedImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private val clipRect = Rect()
    override fun onDraw(canvas: Canvas?) {
        if (clipRect.width() > 0 && clipRect.height() > 0) {
            canvas?.clipRect(clipRect)
        }
        super.onDraw(canvas)
    }

    fun setClipRegion(rect: Rect) {
        clipRect.set(rect)
        invalidate()
    }
}
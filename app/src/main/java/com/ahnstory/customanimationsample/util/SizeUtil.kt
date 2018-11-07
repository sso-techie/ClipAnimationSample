package com.ahnstory.customanimationsample.util

import android.graphics.Rect

/**
 * Created by SSO on 2018. 11. 7..
 * If you have a question, please send an e-mail (sso.techie@gmail.com)
 */

data class Size(val width: Int, val height: Int) {
    val ratio: Float
        get() = if (width > 0) {
            height.toFloat() / width
        } else {
            0f
        }
}

inline fun Rect.getSize(): Size {
    return Size(width(), height())
}
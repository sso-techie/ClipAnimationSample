package com.ahnstory.customanimationsample.util

import android.content.Context
import android.graphics.BitmapFactory

/**
 * Created by SSO on 2018. 11. 7..
 * If you have a question, please send an e-mail (sso.techie@gmail.com)
 */

object DrawableUtil {
    fun getDrawableSize(context: Context, resourceId: Int): Size {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, resourceId, options)
        return Size(options.outWidth, options.outHeight)
    }
}
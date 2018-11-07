package com.ahnstory.customanimationsample

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ahnstory.customanimationsample.animation.ZoomAnimtionController
import com.ahnstory.customanimationsample.util.DrawableUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imageResourceId = R.drawable.portrait

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageButton.setImageResource(imageResourceId)
        imageButton.setOnClickListener {
            startZoomInAnimation(it)
        }
    }

    private fun startZoomInAnimation(view: View) {
        val startRect = Rect(view.left, view.top, view.right, view.bottom)
        val viewSize = Rect(root.left, root.top, root.right, root.bottom)
        val imageSize = DrawableUtil.getDrawableSize(this, imageResourceId)
        val animationController = ZoomAnimtionController(animationView, startRect, viewSize, imageSize)
        animationView.visibility = View.VISIBLE
        animationView.setImageResource(imageResourceId)
        animationController.init()
    }
}

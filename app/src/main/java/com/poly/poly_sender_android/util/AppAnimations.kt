package com.poly.poly_sender_android.util

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.poly.poly_sender_android.R

class AppAnimations(private val context: Context) {
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_bottom_anim
        )
    }

    fun onExpandedFloatingButtonClicked(
        clicked: Boolean,
        mainButton: FloatingActionButton,
        vararg buttons: ExtendedFloatingActionButton
    ): Boolean {
        val al = object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                mainButton.clearAnimation()
                buttons.forEach { button -> button.clearAnimation() }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        }

        rotateClose.setAnimationListener(al)
        toBottom.setAnimationListener(al)

        if (!clicked) {
            for (b in buttons) {
                b.visibility = View.VISIBLE
                b.startAnimation(fromBottom)
                b.isClickable = true
                b.isFocusable = true
            }
            mainButton.startAnimation(rotateOpen)
        } else {
            for (b in buttons) {
                b.visibility = View.GONE
                b.startAnimation(toBottom)
                b.isClickable = false
                b.isFocusable = false
            }
            mainButton.startAnimation(rotateClose)
        }

        return !clicked
    }
}
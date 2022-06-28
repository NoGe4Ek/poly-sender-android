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
    val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_anim
        )
    }
    val toBottom: Animation by lazy {
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
                b.visibility = View.INVISIBLE
                b.startAnimation(toBottom)
                b.isClickable = false
                b.isFocusable = false
            }
            mainButton.startAnimation(rotateClose)
        }

        return !clicked
    }
}
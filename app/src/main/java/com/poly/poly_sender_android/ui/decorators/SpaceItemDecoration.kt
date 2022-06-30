package com.poly.poly_sender_android.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration : ItemDecoration() {
    private val space = 10
    private val bottomNavSpace = 200

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount: Int = parent.childCount
        val itemPosition: Int = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (itemPosition == 0) {
            outRect.top = space
        }
        if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.bottom = bottomNavSpace
        }
    }
}
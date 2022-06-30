package com.poly.poly_sender_android.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration(private val space: Int, private val bottomNavSpace: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount: Int = parent.childCount
        val itemPosition: Int = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.left = space
        outRect.right = space
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
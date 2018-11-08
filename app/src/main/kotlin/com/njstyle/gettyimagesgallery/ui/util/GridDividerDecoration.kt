package com.njstyle.gettyimagesgallery.ui.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View

class GridDividerDecoration(@Px dividerSize: Int, @ColorInt dividerColor: Int, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint()
    private val bounds = Rect()

    init {
        this.dividerPaint.color = dividerColor
        this.dividerPaint.strokeWidth = dividerSize.toFloat()
        this.dividerPaint.style = Style.STROKE
        this.dividerPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val itemCount = parent.adapter!!.itemCount
        val childCount = parent.childCount
        val lastRowChildCount = getLastRowChildCount(itemCount)
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            if (isChildInLastRow(parent, child, itemCount, lastRowChildCount)) {
                continue
            }

            parent.getDecoratedBoundsWithMargins(child, bounds)
            val y = bounds.bottom
            val startX = bounds.left
            val stopX = bounds.right
            canvas.drawLine(startX.toFloat(), y.toFloat(), stopX.toFloat(), y.toFloat(), dividerPaint)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val isRTL = ViewCompat.getLayoutDirection(parent) == ViewCompat.LAYOUT_DIRECTION_RTL
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            if (isChildInLastColumn(parent, child)) {
                continue
            }

            parent.getDecoratedBoundsWithMargins(child, bounds)
            val x = if (isRTL) bounds.left else bounds.right
            val startY = bounds.top
            val stopY = bounds.bottom
            canvas.drawLine(x.toFloat(), startY.toFloat(), x.toFloat(), stopY.toFloat(), dividerPaint)
        }
    }

    private fun getLastRowChildCount(itemCount: Int): Int {
        val gridChildRemainder = itemCount % spanCount
        return if (gridChildRemainder == 0) spanCount else gridChildRemainder
    }

    private fun isChildInLastRow(parent: RecyclerView, child: View, itemCount: Int, lastRowChildCount: Int)
            = parent.getChildAdapterPosition(child) >= itemCount - lastRowChildCount

    private fun isChildInLastColumn(parent: RecyclerView, child: View)
            = parent.getChildAdapterPosition(child) % spanCount == spanCount - 1
}

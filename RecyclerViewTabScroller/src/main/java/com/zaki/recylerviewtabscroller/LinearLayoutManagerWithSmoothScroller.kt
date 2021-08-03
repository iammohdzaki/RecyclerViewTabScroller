package com.zaki.recylerviewtabscroller

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
Created by Mohammad Zaki
on Aug,03 2021
 **/
/**
 * A custom [LinearLayoutManager] that uses a custom [LinearSmoothScroller].
 *
 * When smoothScrollToPosition(targetPos) is called, this custom scroller will
 * snap the recyclerView item in targetPos to the top/start of the list.
 */
class LinearLayoutManagerWithSmoothScroller(
    context: Context,
    @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val smoothController = TopSnappedSmoothScroller(recyclerView.context)
        smoothController.targetPosition = position
        startSmoothScroll(smoothController)
    }


    inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@LinearLayoutManagerWithSmoothScroller.computeScrollVectorForPosition(
                targetPosition
            )
        }

        override fun getVerticalSnapPreference() = SNAP_TO_START

        override fun getHorizontalSnapPreference() = SNAP_TO_START
    }

}
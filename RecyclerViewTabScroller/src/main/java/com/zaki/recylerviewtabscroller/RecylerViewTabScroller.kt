package com.zaki.recylerviewtabscroller

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

/**
Created by Mohammad Zaki
on Aug,03 2021
 **/

/**
 * Custom RecyclerView that synchronizes a tab layout with the recycler view items as
 * a user scrolls through the list.
 *
 * A [TabLayout] must be passed in, as well as a list of the number of items in the recyclerView that
 * correspond to each tab.
 *
 * A [RecyclerViewScrollListener] and  [LinearLayoutManagerWithSmoothScroller] must also be set in order
 * for everything to work properly.
 *
 * Working OverView :
 * 1. Set the tab layout using [setTabLayout]
 * 2. Pass a list containing the # of items in each tab.
 *
 * Example :
 * - TabLayout has 3 tabs
 * - The data set size is 12 items
 *   Suppose we want to group tab 1 - 3 items , tab 2 with - 2 items and so on.
 * - The first 3 items belong to tab 1, the next 2 belong to tab 2, and the last 7 items belong to tab 3
 *
 * 3. Set Items Count for each tab using [setCountItemsByTabIndex]
 * 4. Register Scroll Listener using [addOnScrollListener] and by passing [RecyclerViewScrollListener]
 * 5. Pass an instance of the [LinearLayoutManagerWithSmoothScroller]
 */

class RecyclerViewTabScroller @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var smoothScroll: Boolean = true
) : RecyclerView(context, attrs, defStyleAttr), TabLayout.OnTabSelectedListener {


    private var countItemsByTabIndex: List<Int> = emptyList()
    private var attachedTabLayout: TabLayout? = null
    private var ignoreScroll: Boolean = false
    private var ignoreTabSelect: Boolean = false

    fun setCountItemsByTabIndex(listOfCounts: List<Int>) {
        countItemsByTabIndex = listOfCounts
    }

    fun setTabLayout(tabLayout: TabLayout) {
        attachedTabLayout?.removeOnTabSelectedListener(this)
        attachedTabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout !is LinearLayoutManagerWithSmoothScroller) {
            throw UnsupportedOperationException("onScrollListener must be of type LinearLayoutManagerWithSmoothScroller")
        }
        super.setLayoutManager(layout)
    }

    override fun addOnScrollListener(listener: OnScrollListener) {
        if (listener !is RecyclerViewScrollListener) {
            throw UnsupportedOperationException("onScrollListener must be of type TabSyncedScrollListener")
        }
        super.addOnScrollListener(listener)
    }

    private fun getFirstDataPosInTab(tabIndex: Int): Int {
        if (tabIndex == 0 || countItemsByTabIndex.isEmpty()) {
            return 0
        }

        var prevTabDataPos: Int = countItemsByTabIndex[0]

        for (i in 1 until countItemsByTabIndex.size) {
            if (i == tabIndex) {
                break
            } else {
                prevTabDataPos += countItemsByTabIndex[i]
            }
        }

        return prevTabDataPos
    }

    private fun getTabIndexForPosition(pos: Int): Int {
        if (countItemsByTabIndex.isEmpty()) {
            return 0
        }

        var tabIndex = 0
        var currentTraversedItems = 0

        for (i in countItemsByTabIndex.indices) {
            tabIndex = i
            currentTraversedItems += countItemsByTabIndex[i]
            if (pos < currentTraversedItems) {
                break
            }
        }
        return tabIndex
    }

    private fun updateTabsFromScrollEvent(firstCompletelyVisibleItemPosition: Int) {
        if (firstCompletelyVisibleItemPosition == NO_POSITION) {
            return
        }
        val tabIndexForPos = getTabIndexForPosition(firstCompletelyVisibleItemPosition)
        attachedTabLayout?.let {
            if (tabIndexForPos != it.selectedTabPosition) {
                ignoreTabSelect = true
                val tab = it.getTabAt(tabIndexForPos)
                if (tab != null) {
                    it.selectTab(tab)
                }
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        // ignoreTabSelect will be true if a tab was selected
        // as a result from a user scrolling through the recycleView.
        // Lets ignore this onTabSelected callback in that case
        if (!ignoreTabSelect) {
            val currSelectedTabPos = tab.position
            val index = getFirstDataPosInTab(currSelectedTabPos)
            ignoreScroll = true
            if (smoothScroll) smoothScrollToPosition(index)
            else (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index, 0)
        } else {
            ignoreTabSelect = false
        }
    }

    /**
     * A custom [RecyclerView.OnScrollListener] that calls back to the
     * parent recyclerView to update the current selected tab based on a scroll event
     */
    inner class RecyclerViewScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            // reset the ignoreScroll flag if the recycler is no longer scrolling
            if (newState == SCROLL_STATE_IDLE) {
                ignoreScroll = false
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            if (dx == 0 && dy == 0) {
                return
            }

            // ignoreScroll will be true if the user selected a tab, causing a
            // smoothScrollToPosition action to take place. During this time, lets
            // ignore the onScrolled callbacks
            if (!ignoreScroll) {
                when (val layoutManager = recyclerView.layoutManager) {
                    is LinearLayoutManagerWithSmoothScroller -> updateTabsFromScrollEvent(
                        layoutManager.findFirstCompletelyVisibleItemPosition()
                    )
                    else -> throw IllegalArgumentException("The layoutManager:$layoutManager is unsupported")
                }
            }
        }
    }
}


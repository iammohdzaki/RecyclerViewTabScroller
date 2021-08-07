[![](https://jitpack.io/v/iammohdzaki/RecyclerViewTabScroller.svg)](https://jitpack.io/#iammohdzaki/RecyclerViewTabScroller)

# RecyclerViewTabScroller

Custom RecyclerView that synchronizes a tab layout with the recycler view items as a user scrolls through the list.

<img src="https://github.com/iammohdzaki/RecyclerViewTabScroller/blob/master/RTS_Normal.gif" alt="KTS" width="250" height="550"/>

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.iammohdzaki:RecyclerViewTabScroller:$latest_version'
	}
```
How To Use

Step 1 : Set the tab layout using
```
recyclerView.setTabLayout(Pass Tab Layout Here)
```
Step 2 : Pass a list containing the # of items in each tab.
```
recyclerView.setCountItemsByTabIndex(array list of integers)
```
Example :
 * TabLayout has 3 tabs
 * The data set size is 12 items
 * - Suppose we want to group tab 1 - 3 items , tab 2 with - 2 items and so on.
 * The first 3 items belong to tab 1, the next 2 belong to tab 2, and the last 7 items belong to tab 3

Step 4 : Register Scroll Listener
```
recyclerView.addOnScrollListener(RecyclerViewScrollListener())
```
Step 5 : Set Layout Manager
```
recyclerView.setLayoutManager(LinearLayoutManagerWithSmoothScroller(
                context,
                RecyclerView.VERTICAL,
                false
            ))
```
That's it.

Additional Feature : 
 There are two of scroll supported 
 - With Smooth Scroll
  ```
    recyclerView.setSmoothScroll(true)
  ```
  <img src="https://github.com/iammohdzaki/RecyclerViewTabScroller/blob/master/RTS_SmoothScroll.gif" alt="KTS" width="250" height="550"/>
  
 - Without Smooth Scroll
  ```
    recyclerView.setSmoothScroll(false)
  ```
   <img src="https://github.com/iammohdzaki/RecyclerViewTabScroller/blob/master/RTS_Normal.gif" alt="KTS" width="250" height="550"/>

 License
```
The MIT License (MIT)

Copyright (c) 2021 Mohammad Zaki

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

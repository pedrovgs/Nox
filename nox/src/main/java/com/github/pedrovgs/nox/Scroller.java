/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pedrovgs.nox;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

/**
 * Controls the View scroll and performs all the visual effects needed to indicate the user the
 * view is being scrolled. This implementation is based on GestureDetectorCompat and OverScroller
 * class to implement scroll and fling gestures.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class Scroller {

  private static final int VELOCITY_SCALE = 2;

  private final View view;
  private final int minX;
  private final int maxX;
  private final int minY;
  private final int maxY;
  private final int overSize;

  private GestureDetectorCompat gestureDetector;
  private OverScroller overScroller;
  private boolean isScrollingFast;

  Scroller(View view, int minX, int maxX, int minY, int maxY, int overSize) {
    this.view = view;
    Context context = view.getContext();
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.overSize = overSize;
    this.overScroller = new OverScroller(context);
  }

  /**
   * Returns the current X scroll offset.
   */
  int getOffsetX() {
    return -view.getScrollX();
  }

  /**
   * Returns the current Y scroll offset.
   */
  int getOffsetY() {
    return -view.getScrollY();
  }

  /**
   * Given a MotionEvent instance performs the scroll effect.
   */
  boolean onTouchEvent(MotionEvent event) {
    return getGestureDetector().onTouchEvent(event);
  }

  /**
   * Computes the current scroll using a OverScroller instance and the time lapsed from the
   * previous call. Also controls if the view is performing a fast scroll after a fling gesture.
   */
  void computeScroll() {
    if (!overScroller.computeScrollOffset()) {
      isScrollingFast = false;
      return;
    }

    int distanceX = overScroller.getCurrX() - view.getScrollX();
    int distanceY = overScroller.getCurrY() - view.getScrollY();
    int dX = (int) calculateDx(distanceX);
    int dY = (int) calculateDy(distanceY);
    boolean stopScrolling = dX == 0 && dY == 0;
    if (stopScrolling) {
      isScrollingFast = false;
    }
    view.scrollBy(dX, dY);
  }

  /**
   * Returns the minimum X position configured in construction.
   */
  int getMinX() {
    return minX;
  }

  /**
   * Returns the maximum X position configured in construction.
   */
  int getMaxX() {
    return maxX;
  }

  /**
   * Returns the minimum Y position configured in construction.
   */
  int getMinY() {
    return minY;
  }

  /**
   * Returns the maximum X position configured in construction.
   */
  int getMaxY() {
    return maxY;
  }

  /**
   * Returns the over size configured in construction.
   */
  int getOverSize() {
    return overSize;
  }

  /**
   * Returns true if the view is performing a scroll after a fling gesture.
   */
  boolean isScrollingFast() {
    return isScrollingFast;
  }

  /**
   * Resets the scroll position to the 0,0.
   */
  void reset() {
    view.scrollTo(0, 0);
  }

  /**
   * Returns the GestureDetectorCompat instance where the view should delegate touch events.
   */
  private GestureDetectorCompat getGestureDetector() {
    if (gestureDetector == null) {
      gestureDetector = new GestureDetectorCompat(view.getContext(), gestureListener);
    }
    return gestureDetector;
  }

  /**
   * SimpleOnGestureListener used to perform the scroll effect.
   */
  private final GestureDetector.SimpleOnGestureListener gestureListener =
      new GestureDetector.SimpleOnGestureListener() {
        @Override public boolean onDown(MotionEvent e) {
          resetOverScroller();
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          int dX = (int) calculateDx(distanceX);
          int dY = (int) calculateDy(distanceY);
          view.scrollBy(dX, dY);
          return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
          resetOverScroller();
          int startX = view.getScrollX();
          int startY = view.getScrollY();
          int velX = (int) -velocityX / VELOCITY_SCALE;
          int velY = (int) -velocityY / VELOCITY_SCALE;
          overScroller.fling(startX, startY, velX, velY, minX, maxX, minY, maxY, overSize,
              overSize);
          ViewCompat.postInvalidateOnAnimation(view);
          return false;
        }
      };

  /**
   * Returns the distance in the X axes to perform the scroll taking into account the view
   * boundary.
   */
  private float calculateDx(float distanceX) {
    int currentX = view.getScrollX();
    float nextX = distanceX + currentX;
    boolean isInsideHorizontally = nextX >= minX && nextX <= maxX;
    return isInsideHorizontally ? distanceX : 0;
  }

  /**
   * Returns the distance in the Y axes to perform the scroll taking into account the view
   * boundary.
   */
  private float calculateDy(float distanceY) {
    int currentY = view.getScrollY();
    float nextY = distanceY + currentY;
    boolean isInsideVertically = nextY >= minY && nextY <= maxY;
    return isInsideVertically ? distanceY : 0;
  }

  private void resetOverScroller() {
    overScroller.forceFinished(true);
    isScrollingFast = false;
  }
}

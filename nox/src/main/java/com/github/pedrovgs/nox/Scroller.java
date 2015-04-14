package com.github.pedrovgs.nox;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Controls the NoxView scroll and performs all the visual effects needed to indicate the user the
 * view is being scrolled. This implementation is based on GestureDetectorCompat and OverScroller
 * class to implement scroll and fling gestures.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class Scroller {

  private final View view;

  private GestureDetectorCompat gestureDetector;

  Scroller(View view) {
    this.view = view;
  }

  GestureDetectorCompat getGestureDetector() {
    if (gestureDetector == null) {
      gestureDetector = new GestureDetectorCompat(view.getContext(), mGestureListener);
    }
    return gestureDetector;
  }

  int getOffsetX() {
    return -view.getScrollX();
  }

  int getOffsetY() {
    return -view.getScrollY();
  }

  private final GestureDetector.SimpleOnGestureListener mGestureListener =
      new GestureDetector.SimpleOnGestureListener() {
        @Override public boolean onDown(MotionEvent e) {
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          int dX = (int) distanceX;
          int dY = (int) distanceY;
          view.scrollBy(dX, dY);
          return true;
        }
      };
}

package com.github.pedrovgs.nox;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

/**
 * Controls the NoxView scroll and performs all the visual effects needed to indicate the user the
 * view is being scrolled. This implementation is based on GestureDetectorCompat and OverScroller
 * class to implement scroll and fling gestures.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class Scroller {

  private final View view;

  private OverScroller overScroller;
  private GestureDetectorCompat gestureDetector;

  Scroller(View view) {
    this.view = view;
    overScroller = new OverScroller(view.getContext());
  }

  GestureDetectorCompat getGestureDetector() {
    if (gestureDetector == null) {
      gestureDetector = new GestureDetectorCompat(view.getContext(), mGestureListener);
    }
    return gestureDetector;
  }

  int getCurrentX() {
    return overScroller.getCurrX();
  }

  int getCurrentY() {
    return overScroller.getCurrY();
  }

  void computeScrollOffset() {
    overScroller.computeScrollOffset();
  }

  private final GestureDetector.SimpleOnGestureListener mGestureListener =
      new GestureDetector.SimpleOnGestureListener() {
        @Override public boolean onDown(MotionEvent e) {
          overScroller.forceFinished(true);
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          overScroller.forceFinished(true);
          view.scrollBy((int) distanceX, (int) distanceY);
          return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
          int startX = overScroller.getCurrX();
          int startY = overScroller.getCurrY();
          int velX = (int) velocityX;
          int velY = (int) velocityY;
          overScroller.forceFinished(true);
          int minX = -2000;
          int maxX = 2000;
          int minY = -2000;
          int maxY = 2000;
          overScroller.fling(startX, startY, velX, velY, minX, maxX, minY, maxY,
              view.getWidth() / 2, view.getHeight() / 2);
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }
      };
}

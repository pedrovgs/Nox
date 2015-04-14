package com.github.pedrovgs.nox;

import android.content.Context;
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

  private static final int VELOCITY_SCALE = 2;

  private final View view;

  private GestureDetectorCompat gestureDetector;
  private OverScroller overScroller;

  Scroller(View view) {
    this.view = view;
    Context context = view.getContext();
    overScroller = new OverScroller(context);
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
          overScroller.forceFinished(true);
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

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
          resetOverScroller();
          int startX = view.getScrollX();
          int startY = view.getScrollY();
          int velX = (int) -velocityX / VELOCITY_SCALE;
          int velY = (int) -velocityY / VELOCITY_SCALE;
          int minX = -108000;
          int maxX = 108000;
          int minY = -108000;
          int maxY = 108000;
          overScroller.fling(startX, startY, velX, velY, minX, maxX, minY, maxY, maxX, maxY);
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }
      };

  void computeScroll() {
    if (!overScroller.computeScrollOffset() || overScroller.isFinished()) {
      return;
    }
    int dX = overScroller.getCurrX() - view.getScrollX();
    int dY = overScroller.getCurrY() - view.getScrollY();
    view.scrollBy(dX, dY);
  }

  private void resetOverScroller() {
    overScroller.forceFinished(true);
  }
}

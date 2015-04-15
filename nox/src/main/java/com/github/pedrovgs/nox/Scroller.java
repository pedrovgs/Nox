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
  private final int minX;
  private final int maxX;
  private final int minY;
  private final int maxY;
  private final int overSize;

  private GestureDetectorCompat gestureDetector;
  private OverScroller overScroller;

  Scroller(View view, int minX, int maxX, int minY, int maxY, int overSize) {
    this.view = view;
    Context context = view.getContext();
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.overSize = overSize;
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
          resetOverScroller();
          ViewCompat.postInvalidateOnAnimation(view);
          return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          int dX = calculateDx(distanceX);
          int dY = calculateDy(distanceY);
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
          return true;
        }
      };

  void computeScroll() {
    if (!overScroller.computeScrollOffset() || overScroller.isFinished()) {
      return;
    }
    int distanceX = overScroller.getCurrX() - view.getScrollX();
    int distanceY = overScroller.getCurrY() - view.getScrollY();
    int dX = calculateDx(distanceX);
    int dY = calculateDy(distanceY);
    view.scrollBy(dX, dY);
  }

  private int calculateDx(float distanceX) {
    int currentX = view.getScrollX();
    int nextX = (int) (distanceX + currentX);
    boolean isInsideHorizontally = nextX >= minX && nextX <= maxX;
    return isInsideHorizontally ? (int) distanceX : 0;
  }

  private int calculateDy(float distanceY) {
    int currentY = view.getScrollY();
    int nextY = (int) (distanceY + currentY);
    boolean isInsideVertically = nextY >= minY && nextY <= maxY;
    return isInsideVertically ? (int) distanceY : 0;
  }

  private void resetOverScroller() {
    overScroller.forceFinished(true);
  }
}

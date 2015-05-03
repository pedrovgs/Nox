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

import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Controls the View scale factor and performs all the visual effects needed to indicate the user
 * the view is being zoomed.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class Zoomer {

  private final View view;
  private final float minScaleFactor;
  private final float maxScaleFactor;
  private final ScaleGestureDetector gestureDetector;

  private float scaleFactor = 1;
  private float scaleFocusX;
  private float scaleFocusY;

  Zoomer(View view, float minScaleFactor, float maxScaleFactor) {
    this.view = view;
    this.minScaleFactor = minScaleFactor;
    this.maxScaleFactor = maxScaleFactor;
    ScaleGestureDetector.OnScaleGestureListener listener = getListener();
    gestureDetector = new ScaleGestureDetector(view.getContext(), listener);
  }

  /**
   * Returns the current scale factor NoxView applies to the canvas to perform the zoom effect.
   */
  float getScaleFactor() {
    return scaleFactor;
  }

  /**
   * Returns the current focus offset in the x axis used to perform the scale effect.
   */
  float getScaleFocusX() {
    return scaleFocusX;
  }

  /**
   * Returns the current focus offset int the y axis used to perform the scale effect
   */
  float getScaleFocusY() {
    return scaleFocusY;
  }

  void reset() {
    scaleFactor = 1;
    scaleFocusX = 0;
    scaleFocusY = 0;
  }

  /**
   * Process touch events to perform the scale effect.
   */
  boolean onTouchEvent(MotionEvent event) {
    return gestureDetector.onTouchEvent(event);
  }

  private ScaleGestureDetector.OnScaleGestureListener getListener() {
    return new ScaleGestureDetector.SimpleOnScaleGestureListener() {

      @Override public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        scaleFactor *= scaleGestureDetector.getScaleFactor();
        scaleFocusX = scaleGestureDetector.getFocusX();
        scaleFocusY = scaleGestureDetector.getFocusY();
        if (scaleFactor > maxScaleFactor) {
          scaleFactor = maxScaleFactor;
        } else if (scaleFactor < minScaleFactor) {
          scaleFactor = minScaleFactor;
        }
        ViewCompat.postInvalidateOnAnimation(view);
        return true;
      }
    };
  }
}

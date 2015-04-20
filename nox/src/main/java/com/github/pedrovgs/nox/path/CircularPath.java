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

package com.github.pedrovgs.nox.path;

/**
 * Circular Path implementation used to place NoxItem objects in a circle inside NoxView starting
 * from the center of the view. NoxItem instances in this path will have the same size and the
 * number of NoxItem elements per circle level will follow a linear distribution where "n = 6 *
 * iteration" and one element in the middle of the view.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class CircularPath extends Path {

  private static final double RAD = Math.PI / 180;
  private static final int BASE = 6;

  CircularPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    PathConfig pc = getPathConfig();
    int numberOfItems = pc.getNumberOfElements();
    final float centerX = getCenterX();
    final float centerY = getCenterY();
    float radius = getDistance();
    int item = 0;
    int iteration = 0;
    while (item < numberOfItems) {
      int numberOfElementsPerIteration =
          calculatePositionsForIteration(item, radius, iteration, centerX, centerY);
      item += numberOfElementsPerIteration;
      iteration++;
    }
  }

  protected int getNumberOfElementsPerIteration(int item, int iteration) {
    int numberOfElements = getPathConfig().getNumberOfElements();
    int numberOfElementsPerIteration = Math.max(BASE * iteration, 1);
    numberOfElementsPerIteration = Math.min(numberOfElementsPerIteration, numberOfElements - item);
    return numberOfElementsPerIteration;
  }

  private float getCenterY() {
    PathConfig pc = getPathConfig();
    return pc.getViewHeight() / 2 - pc.getItemSize() / 2 - pc.getItemMargin() / 2;
  }

  private float getCenterX() {
    PathConfig pc = getPathConfig();
    return pc.getViewWidth() / 2 - pc.getItemSize() / 2 - pc.getItemMargin() / 2;
  }

  private float getDistance() {
    PathConfig pc = getPathConfig();
    return pc.getItemSize() + pc.getItemMargin();
  }

  private int calculatePositionsForIteration(int item, float radius, int iteration, float centerX,
      float centerY) {
    int numberOfElementsPerIteration = getNumberOfElementsPerIteration(item, iteration);
    if (numberOfElementsPerIteration == 1) {
      setNoxItemXPosition(item, centerX);
      setNoxItemYPosition(item, centerY);
      return 1;
    }

    float distance = radius * iteration;
    double fullCircleInRads = RAD * 360;
    for (int i = 0; i < numberOfElementsPerIteration; i++) {
      double angle = fullCircleInRads / numberOfElementsPerIteration * i;
      double sin = Math.sin(angle);
      double cos = Math.cos(angle);
      float x = (float) (centerX + (sin * distance));
      float y = (float) (centerY + (cos * distance));
      setNoxItemXPosition(item, x);
      setNoxItemYPosition(item, y);
      item++;
    }
    return numberOfElementsPerIteration;
  }
}

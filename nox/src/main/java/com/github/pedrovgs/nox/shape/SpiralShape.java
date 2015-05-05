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

package com.github.pedrovgs.nox.shape;

/**
 * Spiral Shape implementation used to place NoxItem objects in a equiangular spiral starting from
 * the center of the view. NoxItem instances in this Shape will have the same size. This Shape is
 * based on the Archimedean Spiral.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SpiralShape extends Shape {

  SpiralShape(ShapeConfig shapeConfig) {
    super(shapeConfig);
  }

  @Override public void calculate() {
    ShapeConfig pc = getShapeConfig();
    int numberOfItems = pc.getNumberOfElements();
    float centerY = (pc.getViewHeight() / 2) - (pc.getItemSize() / 2) - (pc.getItemMargin() / 2);
    float centerX = (pc.getViewWidth() / 2) - (pc.getItemSize() / 2) - (pc.getItemMargin() / 2);
    float angle = pc.getItemSize();
    for (int i = 0; i < numberOfItems; i++) {
      setX(centerX, angle, i);
      setY(centerY, angle, i);
    }
  }

  private void setX(float centerX, float angle, int i) {
    double x = centerX + (angle * i * Math.cos(i));
    setNoxItemXPosition(i, (float) x);
  }

  private void setY(float centerY, float angle, int i) {
    double y = centerY + (angle * i * Math.sin(i));
    setNoxItemYPosition(i, (float) y);
  }
}

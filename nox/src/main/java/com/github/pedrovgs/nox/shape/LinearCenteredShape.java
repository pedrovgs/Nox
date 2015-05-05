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
 * Linear centered Shape implementation used to place NoxItem objects in a single line
 * centered in NoxView. NoxItem instances in this shape will have the same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class LinearCenteredShape extends LinearShape {

  LinearCenteredShape(ShapeConfig shapeConfig) {
    super(shapeConfig);
  }

  @Override protected float getFirstItemLeftPosition() {
    float itemSize = getShapeConfig().getItemSize();
    float itemMargin = getShapeConfig().getItemMargin();
    float center = getCenter();
    int numberOfElements = getShapeConfig().getNumberOfElements();
    return center - numberOfElements * (itemSize / 2 + itemMargin / 2);
  }

  private float getCenter() {
    return getShapeConfig().getViewWidth() / 2;
  }
}

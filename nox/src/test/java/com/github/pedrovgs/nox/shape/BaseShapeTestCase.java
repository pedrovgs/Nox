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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Base Shape test case. This test class should be extended by every Shape implementation.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class BaseShapeTestCase {

  private static final int ITEM_SIZE = 10;
  private static final int ITEM_MARGIN = 2;
  protected static final double DELTA = 0.1;

  public abstract Shape getShape(ShapeConfig shapeConfig);

  @Test public void shouldReturnTrueIfViewWidthIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE - 1;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    ShapeConfig shapeConfig = new ShapeConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Shape shape = getShape(shapeConfig);

    assertTrue(shape.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewHeightIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE - 1;
    ShapeConfig shapeConfig = new ShapeConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Shape shape = getShape(shapeConfig);

    assertTrue(shape.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewIsBiggerOrEqualsThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    ShapeConfig shapeConfig = new ShapeConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Shape shape = getShape(shapeConfig);

    assertTrue(shape.isItemInsideView(0));
  }

  protected ShapeConfig givenAShapeConfig(int numberOfElements, int viewWidth, int viewHeight,
      float itemSize, float itemMargin) {
    return new ShapeConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

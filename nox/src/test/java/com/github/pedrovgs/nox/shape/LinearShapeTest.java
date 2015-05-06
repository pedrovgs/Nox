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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class LinearShapeTest extends BaseShapeTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Shape shape;

  @Before public void setUp() {
    ShapeConfig shapeConfig =
        givenAShapeConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);
  }

  @Override public Shape getShape(ShapeConfig shapeConfig) {
    return ShapeFactory.getLinearShape(shapeConfig);
  }

  @Test public void shouldUseSameTopForEveryElement() {
    int numberOfElements = 10;
    ShapeConfig shapeConfig =
        givenAShapeConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
            ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);

    shape.calculate();

    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2);
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedTop, shape.getYForItemAtPosition(i), DELTA);
    }
  }

  @Test public void shouldCalculateLeftPositionsUsingOneLinearDistributionUsingItemSizeAndMargin() {
    int numberOfElements = 10;
    ShapeConfig shapeConfig =
        givenAShapeConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
            ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);

    shape.calculate();

    float expectedLeft = ANY_ITEM_MARGIN;
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedLeft, shape.getXForItemAtPosition(i), DELTA);
      expectedLeft += ANY_ITEM_SIZE + ANY_ITEM_MARGIN;
    }
  }

  @Test public void shouldCalculateTheCorrectLeftPositionForOneElement() {
    shape.calculate();

    float left = shape.getXForItemAtPosition(0);

    assertEquals(ANY_ITEM_MARGIN, left, DELTA);
  }

  @Test public void shouldReturnTrueJustForTheElementsInsideTheView() {
    ShapeConfig shapeConfig =
        givenAShapeConfig(11, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getShape(shapeConfig);

    shape.calculate();

    assertElementsAtPositionAreInsideTheView(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    assertElementsAtPositionAreOutsizeTheView(10);
  }

  private void assertElementsAtPositionAreInsideTheView(int... positions) {
    for (int position : positions) {
      assertTrue(shape.isItemInsideView(position));
    }
  }

  private void assertElementsAtPositionAreOutsizeTheView(int... positions) {
    for (int position : positions) {
      assertFalse(shape.isItemInsideView(position));
    }
  }
}

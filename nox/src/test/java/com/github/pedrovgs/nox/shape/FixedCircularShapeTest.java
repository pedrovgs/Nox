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

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class FixedCircularShapeTest extends BasePathTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Shape shape;

  @Before public void setUp() {
    ShapeConfig shapeConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);
  }

  @Override public Shape getPath(ShapeConfig shapeConfig) {
    return ShapeFactory.getFixedCircularShape(shapeConfig);
  }

  @Test public void shouldReturnTheMiddleOfTheViewAsPositionForJustOneElement() {
    ShapeConfig shapeConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);

    shape.calculate();

    float expectedLeft = (ANY_VIEW_WIDTH / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedLeft, shape.getXForItemAtPosition(0), DELTA);
    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedTop, shape.getYForItemAtPosition(0), DELTA);
  }

  @Test public void shouldPositionElementsFollowingACircle() {
    ShapeConfig shapeConfig =
        givenPathConfig(5, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);

    shape.calculate();

    float centerX = ANY_VIEW_WIDTH / 2 - ANY_ITEM_SIZE / 2 - ANY_ITEM_MARGIN / 2;
    float centerY = ANY_VIEW_HEIGHT / 2 - ANY_ITEM_SIZE / 2 - ANY_ITEM_MARGIN / 2;
    float radius = ANY_ITEM_SIZE + ANY_ITEM_MARGIN;
    assertElementPositions(0, centerX, centerY, shape);
    assertElementPositions(1, centerX, centerY + radius, shape);
    assertElementPositions(2, centerX + radius, centerY, shape);
    assertElementPositions(3, centerX, centerY - radius, shape);
    assertElementPositions(4, centerX - radius, centerY, shape);
  }

  private void assertElementPositions(int position, float x, float y, Shape shape) {
    assertEquals(x, shape.getXForItemAtPosition(position), DELTA);
    assertEquals(y, shape.getYForItemAtPosition(position), DELTA);
  }
}

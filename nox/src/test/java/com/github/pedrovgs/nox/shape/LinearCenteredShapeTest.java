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
public class LinearCenteredShapeTest extends BasePathTestCase {

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
    return ShapeFactory.getLinearCenteredShape(shapeConfig);
  }

  @Test public void shouldUseSameTopForEveryElement() {
    int numberOfElements = 10;
    ShapeConfig shapeConfig =
        givenPathConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
            ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);

    shape.calculate();

    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2);
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedTop, shape.getYForItemAtPosition(i), DELTA);
    }
  }

  @Test
  public void shouldCalculateLeftPositionsUsingOneCenteredLinearDistributionUsingItemSizeAndMargin() {
    int numberOfElements = 10;
    ShapeConfig shapeConfig =
        givenPathConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
            ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);

    shape.calculate();

    float expectedLeft = getFirstItemLeftPosition(shapeConfig);
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedLeft, shape.getXForItemAtPosition(i), DELTA);
      expectedLeft += ANY_ITEM_SIZE + ANY_ITEM_MARGIN;
    }
  }

  @Test public void shouldReturnTheMiddleOfTheViewAsLeftPositionForJustOneElement() {
    ShapeConfig shapeConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    shape = getPath(shapeConfig);

    shape.calculate();

    float expectedLeft = (ANY_VIEW_WIDTH / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedLeft, shape.getXForItemAtPosition(0), DELTA);
  }

  private float getFirstItemLeftPosition(ShapeConfig shapeConfig) {
    float itemSize = shapeConfig.getItemSize();
    float itemMargin = shapeConfig.getItemMargin();
    float center = shapeConfig.getViewWidth() / 2;
    int numberOfElements = shapeConfig.getNumberOfElements();
    return center - numberOfElements * (itemSize / 2 + itemMargin / 2);
  }
}

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

import com.github.pedrovgs.nox.doubles.FakeShape;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class BoundaryShapeTest {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;
  private static final float ANY_X_POSITION = 1;
  private static final double DELTA = 0.1;
  private static final float ANY_Y_POSITION = 1;
  private static final int ANY_ITEM_POSITION = 0;
  private static final int ANY_BIG_OFFSET = 1000;

  private FakeShape path;

  @Before public void setUp() {
    ShapeConfig shapeConfig =
        new ShapeConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = new FakeShape(shapeConfig);
  }

  @Test public void shouldReturnFalseIfItemIsAtTheRightAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH + ANY_ITEM_SIZE + 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsAtTheLeftAndOutsideTheView() {
    float x = -ANY_ITEM_SIZE - 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsAboveAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = -ANY_ITEM_SIZE - 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsBelowAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT + ANY_ITEM_SIZE + 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheLeftPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH - 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheRightPartOfTheItemIsInsideTheView() {
    float x = -1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheTopPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT - 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheBottomPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = -1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldUseMinXConfiguredPositionForEveryElementAsMinXUsingItemAndViewSize() {
    path.setNoxItemXPosition(ANY_ITEM_POSITION, ANY_X_POSITION);
    path.setNoxItemXPosition(ANY_ITEM_POSITION, ANY_X_POSITION - 1);

    float expectedX = ANY_X_POSITION - 1 - ANY_ITEM_MARGIN;
    assertEquals(expectedX, path.getMinX(), DELTA);
  }

  @Test public void shouldUseMaxXConfiguredPositionForEveryElementAsMaxXUsingItemAndViewSize() {
    path.setNoxItemXPosition(ANY_ITEM_POSITION, ANY_X_POSITION);
    path.setNoxItemXPosition(ANY_ITEM_POSITION, ANY_X_POSITION + 1);

    float expectedX = ANY_X_POSITION + 1 + ANY_ITEM_MARGIN + ANY_ITEM_SIZE - ANY_VIEW_WIDTH;
    assertEquals(expectedX, path.getMaxX(), DELTA);
  }

  @Test public void shouldUseMinYConfiguredPositionForEveryElementAsMinYUsingItemAndViewSize() {
    path.setNoxItemYPosition(ANY_ITEM_POSITION, ANY_Y_POSITION);
    path.setNoxItemYPosition(ANY_ITEM_POSITION, ANY_Y_POSITION - 1);

    float expectedY = ANY_Y_POSITION - 1 - ANY_ITEM_MARGIN;
    assertEquals(expectedY, path.getMinY(), DELTA);
  }

  @Test public void shouldUseMaxYConfiguredPositionForEveryElementAsMaxYUsingItemAndViewSize() {
    path.setNoxItemYPosition(ANY_ITEM_POSITION, ANY_Y_POSITION);
    path.setNoxItemYPosition(ANY_ITEM_POSITION, ANY_Y_POSITION + 1);

    float expected = ANY_Y_POSITION + 1 + ANY_ITEM_MARGIN + ANY_ITEM_SIZE - ANY_VIEW_HEIGHT;
    assertEquals(expected, path.getMaxY(), DELTA);
  }

  @Test public void shouldReturnFalseIfViewIsInsideThePositionButABigXOffsetHasBeenApplied() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();
    path.setOffset(ANY_BIG_OFFSET, 0);

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfViewIsInsideThePositionButABigYOffsetHasBeenApplied() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();
    path.setOffset(0, ANY_BIG_OFFSET);

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewIsInsideThePositionButALittleXOffsetHasBeenApplied() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();
    path.setOffset(1, 0);

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewIsInsideThePositionButALittleYOffsetHasBeenApplied() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();
    path.setOffset(0, 1);

    assertTrue(path.isItemInsideView(0));
  }

  private void givenElementsAreInPosition(float x, float y) {
    path.setXPosition(x);
    path.setYPosition(y);
  }
}

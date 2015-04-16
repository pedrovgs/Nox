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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class SpiralPathTest extends BasePathTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Path path;

  @Override public Path getPath(PathConfig pathConfig) {
    return PathFactory.getSpiralPath(pathConfig);
  }

  @Test public void shouldReturnTheMiddleOfTheViewAsPositionForJustOneElement() {
    PathConfig pathConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);

    path.calculate();

    float expectedLeft = (ANY_VIEW_WIDTH / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedLeft, path.getLeftForItemAtPosition(0), DELTA);
    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedTop, path.getTopForItemAtPosition(0), DELTA);
  }

  @Test public void shouldConfigureElementsFollowingAnArchimedeanSpiral() {
    PathConfig pathConfig =
        givenPathConfig(10, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);

    path.calculate();

    assertElementsConformAnSpiral(pathConfig);
  }

  private void assertElementsConformAnSpiral(PathConfig pathConfig) {
    for (int i = 0; i < pathConfig.getNumberOfElements(); i++) {
      float expectedLeft = getExpectedLeftAtPosition(i, pathConfig);
      float expectedTop = getExpectedTopAtPosition(i, pathConfig);
      assertEquals(expectedLeft, path.getLeftForItemAtPosition(i), DELTA);
      assertEquals(expectedTop, path.getTopForItemAtPosition(i), DELTA);
    }
  }

  private float getExpectedLeftAtPosition(int i, PathConfig pathConfig) {
    float angle = pathConfig.getFirstItemSize();
    float centerX = (pathConfig.getViewWidth() / 2)
        - (pathConfig.getFirstItemSize() / 2)
        - (pathConfig.getFirstItemMargin() / 2);
    return (float) (centerX + (angle * i * Math.cos(i)));
  }

  private float getExpectedTopAtPosition(int i, PathConfig pathConfig) {
    float angle = pathConfig.getFirstItemSize();
    float centerY = (pathConfig.getViewHeight() / 2)
        - (pathConfig.getFirstItemSize() / 2)
        - (pathConfig.getFirstItemMargin() / 2);
    return (float) (centerY + (angle * i * Math.sin(i)));
  }
}

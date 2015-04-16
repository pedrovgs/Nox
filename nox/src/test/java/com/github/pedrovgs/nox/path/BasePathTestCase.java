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

import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class BasePathTestCase {

  private static final int ITEM_SIZE = 10;
  private static final int ITEM_MARGIN = 2;
  protected static final double DELTA = 0.1;

  public abstract Path getPath(PathConfig pathConfig);

  @Test public void shouldReturnTrueIfViewWidthIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE - 1;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewHeightIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE - 1;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewIsBiggerOrEqualsThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  protected PathConfig givenPathConfig(int numberOfElements, int viewWidth, int viewHeight,
      float itemSize, float itemMargin) {
    return new PathConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

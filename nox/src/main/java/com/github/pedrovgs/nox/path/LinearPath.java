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
 * Linear Path implementation used to place NoxItem objects in a single line in
 * NoxView starting from the left side of the view. NoxItem instances in this path will have the
 * same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class LinearPath extends Path {

  LinearPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    int numberOfItems = getPathConfig().getNumberOfElements();
    float height = (getPathConfig().getViewHeight() / 2) - (getPathConfig().getFirstItemSize() / 2);

    float itemWidth = getPathConfig().getFirstItemSize();
    float viewMargin = getPathConfig().getFirstItemMargin();
    float x = getFirstItemLeftPosition();

    for (int i = 0; i < numberOfItems; i++) {
      setNoxItemYPosition(i, height);
      setNoxItemXPosition(i, x);
      x += itemWidth + viewMargin;
    }
  }

  protected float getFirstItemLeftPosition() {
    return getPathConfig().getFirstItemMargin();
  }
}

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
 * Contains all the information needed to create a Path instance.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class PathConfig {

  private final int numberOfElements;
  private final int viewWidth;
  private final int viewHeight;
  private final float itemSize;
  private final float itemMargin;

  public PathConfig(int numberOfElements, int viewWidth, int viewHeight, float itemSize,
      float itemMargin) {
    this.numberOfElements = numberOfElements;
    this.viewWidth = viewWidth;
    this.viewHeight = viewHeight;
    this.itemSize = itemSize;
    this.itemMargin = itemMargin;
  }

  public int getNumberOfElements() {
    return numberOfElements;
  }

  public int getViewWidth() {
    return viewWidth;
  }

  public int getViewHeight() {
    return viewHeight;
  }

  public float getItemSize() {
    return itemSize;
  }

  public float getItemMargin() {
    return itemMargin;
  }
}

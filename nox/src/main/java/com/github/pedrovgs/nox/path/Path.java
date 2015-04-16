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
 * Describes where NoxView has to draw NoxItems inside the space available in NoxView.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class Path {

  private final PathConfig pathConfig;
  private float[] noxItemsXPositions;
  private float[] noxItemsYPositions;
  private int offsetX;
  private int offsetY;

  public Path(PathConfig pathConfig) {
    this.pathConfig = pathConfig;
    int numberOfElements = pathConfig.getNumberOfElements();
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  public void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  public abstract void calculate();

  public float getLeftForItemAtPosition(int position) {
    return noxItemsXPositions[position];
  }

  public float getTopForItemAtPosition(int position) {
    return noxItemsYPositions[position];
  }

  public boolean isItemInsideView(int position) {
    float x = getLeftForItemAtPosition(position) + offsetX;
    float y = getTopForItemAtPosition(position) + offsetY;
    float itemSize = pathConfig.getFirstItemSize();
    boolean matchesHorizontally = x + itemSize >= 0 && x <= pathConfig.getViewWidth();
    boolean matchesVertically = y + itemSize >= 0 && y <= pathConfig.getViewHeight();
    return matchesHorizontally && matchesVertically;
  }

  public int getMinX() {
    return 0;
  }

  public int getMaxX() {
    return 0;
  }

  public int getMinY() {
    return 0;
  }

  public int getMaxY() {
    return 0;
  }

  public int getOverSize() {
    return (int) pathConfig.getFirstItemMargin();
  }

  protected PathConfig getPathConfig() {
    return pathConfig;
  }

  protected void setNoxItemLeftPosition(int position, float left) {
    noxItemsXPositions[position] = left;
  }

  protected void setNoxItemTopPosition(int position, float top) {
    noxItemsYPositions[position] = top;
  }
}

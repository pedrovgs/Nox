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
 * Describes where NoxView has to draw NoxItems inside the space available in NoxView. Information
 * needed to indicate the exact position for every item can be retrieved from the PathConfig object
 * passed as argument during the object construction.
 *
 * The offset attributes indicates the scroll performed by the user. This method is called
 * periodically when the user scrolls the view containing this path.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class Path {

  private final PathConfig pathConfig;

  private float[] noxItemsXPositions;
  private float[] noxItemsYPositions;
  private int offsetX;
  private int offsetY;
  private int minX;
  private int maxX;
  private int minY;
  private int maxY;

  public Path(PathConfig pathConfig) {
    this.pathConfig = pathConfig;
    int numberOfElements = pathConfig.getNumberOfElements();
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  /**
   * Configures the new offset to apply to the path.
   */
  public void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  /**
   * Path extensions should implement this method and configure the position in the x and y axis
   * for
   * every NoxItem.
   */
  public abstract void calculate();

  /**
   * Returns the X position of a NoxItem for the current path.
   */
  public final float getXForItemAtPosition(int position) {
    return noxItemsXPositions[position];
  }

  /**
   * Returns the Y position of a NoxItem for the current path.
   */
  public final float getYForItemAtPosition(int position) {
    return noxItemsYPositions[position];
  }

  /**
   * Returns true if the view should be rendered inside the view window.
   */
  public final boolean isItemInsideView(int position) {
    float x = getXForItemAtPosition(position) + offsetX;
    float y = getYForItemAtPosition(position) + offsetY;
    float itemSize = pathConfig.getItemSize();
    boolean matchesHorizontally = x + itemSize >= 0 && x <= pathConfig.getViewWidth();
    boolean matchesVertically = y + itemSize >= 0 && y <= pathConfig.getViewHeight();
    return matchesHorizontally && matchesVertically;
  }

  /**
   * Returns the minimum X position the view should show during the scroll process.
   */
  public final int getMinX() {
    return (int) (minX - getPathConfig().getItemMargin());
  }

  /**
   * Returns the maximum X position the view should show during the scroll process.
   */
  public final int getMaxX() {
    return (int) (maxX + +getPathConfig().getItemSize() + getPathConfig().getItemMargin()
        - getPathConfig().getViewWidth());
  }

  /**
   * Returns the minimum Y position the view should show during the scroll process.
   */
  public final int getMinY() {
    return (int) (minY - getPathConfig().getItemMargin());
  }

  /**
   * Returns the maximum Y position the view should show during the scroll process.
   */
  public final int getMaxY() {
    return (int) (maxY + getPathConfig().getItemMargin() + getPathConfig().getItemSize()
        - getPathConfig().getViewHeight());
  }

  /**
   * Returns the over scroll used by the view during the fling process.
   */
  public int getOverSize() {
    return (int) pathConfig.getItemMargin();
  }

  /**
   * Returns the PathConfig used to create this path.
   */
  public final PathConfig getPathConfig() {
    return pathConfig;
  }

  /**
   * Returns the number of elements the Path is using to calculate NoxItems positions.
   */
  public int getNumberOfElements() {
    return getPathConfig().getNumberOfElements();
  }

  /**
   * Configures the number of element the Path is going to use to calculate NoxItems positions.
   * This method resets the previous position calculus.
   */
  public void setNumberOfElements(int numberOfElements) {
    this.pathConfig.setNumberOfElements(numberOfElements);
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  /**
   * Configures the X position for a given NoxItem indicated with the item position. This method
   * uses two counters to calculate the Path minimum and maximum X position used to configure the
   * Path scroll.
   */
  protected final void setNoxItemXPosition(int position, float x) {
    noxItemsXPositions[position] = x;
    minX = (int) Math.min(x, minX);
    maxX = (int) Math.max(x, maxX);
  }

  /**
   * Configures the Y position for a given NoxItem indicated with the item position. This method
   * uses two counters to calculate the Path minimum and maximum Y position used to configure the
   * Path scroll.
   */
  protected final void setNoxItemYPosition(int position, float y) {
    noxItemsYPositions[position] = y;
    minY = (int) Math.min(y, minY);
    maxY = (int) Math.max(y, maxY);
  }
}

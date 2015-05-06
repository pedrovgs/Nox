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

/**
 * Describes where NoxView has to draw NoxItems inside the space available in NoxView. Information
 * needed to indicate the exact position for every item can be retrieved from the ShapeConfig
 * object
 * passed as argument during the object construction.
 *
 * The offset attributes indicates the scroll performed by the user. This method is called
 * periodically when the user scrolls the view containing this shape.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class Shape {

  private final ShapeConfig shapeConfig;

  private float[] noxItemsXPositions;
  private float[] noxItemsYPositions;
  private int offsetX;
  private int offsetY;
  private int minX;
  private int maxX;
  private int minY;
  private int maxY;

  public Shape(ShapeConfig shapeConfig) {
    this.shapeConfig = shapeConfig;
    int numberOfElements = shapeConfig.getNumberOfElements();
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  /**
   * Configures the new offset to apply to the Shape.
   */
  public void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  /**
   * Shape extensions have implement this method and configure the position in the x and y axis for
   * every NoxItem.
   */
  public abstract void calculate();

  /**
   * Returns the X position of a NoxItem for the current Shape.
   */
  public final float getXForItemAtPosition(int position) {
    return noxItemsXPositions[position];
  }

  /**
   * Returns the Y position of a NoxItem for the current Shape.
   */
  public final float getYForItemAtPosition(int position) {
    return noxItemsYPositions[position];
  }

  /**
   * Returns true if the view should be rendered inside the view window taking into account the
   * offset applied by the scroll effect.
   */
  public final boolean isItemInsideView(int position) {
    float x = (getXForItemAtPosition(position) + offsetX);
    float y = (getYForItemAtPosition(position) + offsetY);
    float itemSize = getNoxItemSize();
    int viewWidth = shapeConfig.getViewWidth();
    boolean matchesHorizontally = x + itemSize >= 0 && x <= viewWidth;
    float viewHeight = shapeConfig.getViewHeight();
    boolean matchesVertically = y + itemSize >= 0 && y <= viewHeight;
    return matchesHorizontally && matchesVertically;
  }

  /**
   * Returns the minimum X position the view should show during the scroll process.
   */
  public final int getMinX() {
    return (int) (this.minX - getNoxItemMargin());
  }

  /**
   * Returns the maximum X position the view should show during the scroll process.
   */
  public final int getMaxX() {
    return (int) (this.maxX + getNoxItemSize() + getNoxItemMargin()
        - getShapeConfig().getViewWidth());
  }

  /**
   * Returns the minimum Y position the view should show during the scroll process.
   */
  public final int getMinY() {
    return (int) (this.minY - getNoxItemMargin());
  }

  /**
   * Returns the maximum Y position the view should show during the scroll process.
   */
  public final int getMaxY() {
    return (int) (this.maxY + getNoxItemMargin() + getNoxItemSize()
        - getShapeConfig().getViewHeight());
  }

  /**
   * Returns the over scroll used by the view during the fling process. By default this value will
   * be equals to the configured margin.
   */
  public int getOverSize() {
    return (int) shapeConfig.getItemMargin();
  }

  /**
   * Returns the ShapeConfig used to create this Shape.
   */
  public final ShapeConfig getShapeConfig() {
    return shapeConfig;
  }

  /**
   * Returns the number of elements the Shape is using to calculate NoxItems positions.
   */
  public int getNumberOfElements() {
    return getShapeConfig().getNumberOfElements();
  }

  /**
   * Returns the position of the NoxView if any of the previously configured NoxItem instances is
   * hit. If there is no any NoxItem hit this method returns -1.
   */
  public int getNoxItemHit(float x, float y) {
    int noxItemPosition = -1;
    for (int i = 0; i < getNumberOfElements(); i++) {
      float noxItemX = getXForItemAtPosition(i) + offsetX;
      float noxItemY = getYForItemAtPosition(i) + offsetY;
      float itemSize = getNoxItemSize();
      boolean matchesHorizontally = x >= noxItemX && x <= noxItemX + itemSize;
      boolean matchesVertically = y >= noxItemY && y <= noxItemY + itemSize;
      if (matchesHorizontally && matchesVertically) {
        noxItemPosition = i;
        break;
      }
    }
    return noxItemPosition;
  }

  /**
   * Configures the number of element the Shape is going to use to calculate NoxItems positions.
   * This method resets the previous position calculus.
   */
  public void setNumberOfElements(int numberOfElements) {
    this.shapeConfig.setNumberOfElements(numberOfElements);
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  /**
   * Configures the X position for a given NoxItem indicated with the item position. This method
   * uses two counters to calculate the Shape minimum and maximum X position used to configure the
   * Shape scroll.
   */
  protected final void setNoxItemXPosition(int position, float x) {
    noxItemsXPositions[position] = x;
    minX = (int) Math.min(x, minX);
    maxX = (int) Math.max(x, maxX);
  }

  /**
   * Configures the Y position for a given NoxItem indicated with the item position. This method
   * uses two counters to calculate the Shape minimum and maximum Y position used to configure the
   * Shape scroll.
   */
  protected final void setNoxItemYPosition(int position, float y) {
    noxItemsYPositions[position] = y;
    minY = (int) Math.min(y, minY);
    maxY = (int) Math.max(y, maxY);
  }

  /**
   * Returns the NoxItem size taking into account the scale factor.
   */
  protected float getNoxItemSize() {
    return getShapeConfig().getItemSize();
  }

  /**
   * Returns the NoxIte margin taking into account the scale factor.
   */
  private float getNoxItemMargin() {
    return getShapeConfig().getItemMargin();
  }
}

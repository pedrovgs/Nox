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

  //TODO: This methods should be abstract
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

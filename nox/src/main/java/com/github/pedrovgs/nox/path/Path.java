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

  public Path(PathConfig pathConfig) {
    this.pathConfig = pathConfig;
    int numberOfElements = pathConfig.getNumberOfElements();
    this.noxItemsXPositions = new float[numberOfElements];
    this.noxItemsYPositions = new float[numberOfElements];
  }

  protected PathConfig getPathConfig() {
    return pathConfig;
  }

  public float getLeftForItemAtPosition(int position) {
    return noxItemsXPositions[position];
  }

  protected void setNoxItemLeftPosition(int position, float left) {
    noxItemsXPositions[position] = left;
  }

  public float getTopForItemAtPosition(int position) {
    return noxItemsYPositions[position];
  }

  protected void setNoxItemTopPosition(int position, float top) {
    noxItemsYPositions[position] = top;
  }

  public boolean isItemInsideView(int position) {
    float x = getLeftForItemAtPosition(position);
    float y = getTopForItemAtPosition(position);
    boolean matchesHorizontally =
        x >= 0 && (x + pathConfig.getFirstItemSize()) <= pathConfig.getViewWidth();
    boolean matchesVertically =
        y >= 0 && (y + pathConfig.getFirstItemSize() <= pathConfig.getViewHeight());
    return matchesHorizontally && matchesVertically;
  }

  public abstract void calculate();
}

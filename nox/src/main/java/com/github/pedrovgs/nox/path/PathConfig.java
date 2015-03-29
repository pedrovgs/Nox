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
  private final float firstItemSize;
  private final float firstItemMargin;

  public PathConfig(int numberOfElements, int viewWidth, int viewHeight, float firstItemSize,
      float firstItemMargin) {
    this.numberOfElements = numberOfElements;
    this.viewWidth = viewWidth;
    this.viewHeight = viewHeight;
    this.firstItemSize = firstItemSize;
    this.firstItemMargin = firstItemMargin;
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

  public float getFirstItemSize() {
    return firstItemSize;
  }

  public float getFirstItemMargin() {
    return firstItemMargin;
  }
}

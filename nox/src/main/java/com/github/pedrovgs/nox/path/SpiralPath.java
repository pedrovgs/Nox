package com.github.pedrovgs.nox.path;

/**
 * Spiral Path implementation used to place NoxItem objects in a equiangular spiral starting from
 * the center of the view. NoxItem instances in this path will have the same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SpiralPath extends Path {

  SpiralPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    int numberOfItems = getPathConfig().getNumberOfElements();
    float centerY =
        (getPathConfig().getViewHeight() / 2) - (getPathConfig().getFirstItemSize() / 2);
    float centerX = (getPathConfig().getViewWidth() / 2) + (getPathConfig().getFirstItemSize() / 2);
    float angle = getPathConfig().getFirstItemSize();
    for (int i = 0; i < numberOfItems; i++) {
      double x = centerX + (angle * i * Math.cos(i));
      setNoxItemLeftPosition(i, (float) x);
      double y = centerY + (angle * i * Math.sin(i));
      setNoxItemTopPosition(i, (float) y);
    }
  }

  protected float getFirstItemLeftPosition() {
    return getPathConfig().getFirstItemMargin();
  }
}

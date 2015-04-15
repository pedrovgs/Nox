package com.github.pedrovgs.nox.path;

/**
 * Spiral Path implementation used to place NoxItem objects in a equiangular spiral starting from
 * the center of the view. NoxItem instances in this path will have the same size. This path is
 * based on the Archimedean Spiral.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SpiralPath extends Path {

  private int minX;
  private int maxX;
  private int minY;
  private int maxY;

  SpiralPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    PathConfig pc = getPathConfig();
    int numberOfItems = pc.getNumberOfElements();
    float centerY =
        (pc.getViewHeight() / 2) - (pc.getFirstItemSize() / 2) - (pc.getFirstItemMargin() / 2);
    float centerX =
        (pc.getViewWidth() / 2) - (pc.getFirstItemSize() / 2) - (pc.getFirstItemMargin() / 2);
    float angle = pc.getFirstItemSize();
    for (int i = 0; i < numberOfItems; i++) {
      setX(centerX, angle, i);
      setY(centerY, angle, i);
    }
  }

  private void setX(float centerX, float angle, int i) {
    double x = centerX + (angle * i * Math.cos(i));
    setNoxItemLeftPosition(i, (float) x);
    minX = (int) Math.min(x, minX);
    maxX = (int) Math.max(x, maxX);
  }

  private void setY(float centerY, float angle, int i) {
    double y = centerY + (angle * i * Math.sin(i));
    setNoxItemTopPosition(i, (float) y);
    minY = (int) Math.min(y, minY);
    maxY = (int) Math.max(y, maxY);
  }

  @Override public int getMinX() {
    return minX;
  }

  @Override public int getMaxX() {
    return (int) (maxX + getPathConfig().getFirstItemSize());
  }

  @Override public int getMinY() {
    return minY;
  }

  @Override public int getMaxY() {
    return (int) (maxY + getPathConfig().getFirstItemSize());
  }
}

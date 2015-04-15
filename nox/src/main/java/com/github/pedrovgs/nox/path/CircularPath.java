package com.github.pedrovgs.nox.path;

/**
 * Circular Path implementation used to place NoxItem objects in a circle inside NoxView starting
 * from the center of the view. NoxItem instances in this path will have the same size and the
 * number of NoxItem elements per circle level will follow a linear distribution where "n = 6 *
 * iteration" and one element in the middle of the view.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class CircularPath extends Path {

  private static final double RAD = Math.PI / 180;
  private static final int BASE = 6;

  CircularPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    PathConfig pc = getPathConfig();
    int numberOfItems = pc.getNumberOfElements();
    final float centerX = getCenterX();
    final float centerY = getCenterY();
    float radius = getDistance();
    int item = 0;
    int iteration = 0;
    while (item < numberOfItems) {
      int numberOfElementsPerIteration =
          calculatePositionsForIteration(item, radius, iteration, centerX, centerY);
      item += numberOfElementsPerIteration;
      iteration++;
    }
  }

  private float getCenterY() {
    PathConfig pc = getPathConfig();
    return pc.getViewHeight() / 2 - pc.getFirstItemSize() / 2 - pc.getFirstItemMargin() / 2;
  }

  private float getCenterX() {
    PathConfig pc = getPathConfig();
    return pc.getViewWidth() / 2 - pc.getFirstItemSize() / 2 - pc.getFirstItemMargin() / 2;
  }

  private float getDistance() {
    PathConfig pc = getPathConfig();
    return pc.getFirstItemSize() + pc.getFirstItemMargin();
  }

  private int calculatePositionsForIteration(int item, float radius, int iteration, float centerX,
      float centerY) {
    int numberOfElementsPerIteration = getNumberOfElementsPerIteration(item, iteration);
    if (numberOfElementsPerIteration == 1) {
      setNoxItemLeftPosition(item, centerX);
      setNoxItemTopPosition(item, centerY);
      return 1;
    }

    float distance = radius * iteration;
    double fullCircleInRads = RAD * 360;
    for (int i = 0; i < numberOfElementsPerIteration; i++) {
      double angle = fullCircleInRads / numberOfElementsPerIteration * i;
      double sin = Math.sin(angle);
      double cos = Math.cos(angle);
      float x = (float) (centerX + (sin * distance));
      float y = (float) (centerY + (cos * distance));
      setNoxItemLeftPosition(item, x);
      setNoxItemTopPosition(item, y);
      item++;
    }
    return numberOfElementsPerIteration;
  }

  protected int getNumberOfElementsPerIteration(int item, int iteration) {
    int numberOfElements = getPathConfig().getNumberOfElements();
    int numberOfElementsPerIteration = Math.max(BASE * iteration, 1);
    numberOfElementsPerIteration = Math.min(numberOfElementsPerIteration, numberOfElements - item);
    return numberOfElementsPerIteration;
  }

  @Override public int getMinX() {
    int numberOfIterations = getNumberOfIterations();
    float iterationDistance = getDistance();
    float radius = iterationDistance * numberOfIterations;
    return (int) (getCenterX() - radius);
  }

  @Override public int getMaxX() {
    int numberOfIterations = getNumberOfIterations();
    float iterationDistance = getDistance();
    float radius = iterationDistance * numberOfIterations;
    int viewWidth = getPathConfig().getViewWidth();
    float totalItemSize = getPathConfig().getFirstItemMargin() + getPathConfig().getFirstItemSize();
    return (int) (getCenterX() + radius - viewWidth + totalItemSize);
  }

  @Override public int getMinY() {
    int numberOfIterations = getNumberOfIterations();
    float iterationDistance = getDistance();
    float radius = iterationDistance * numberOfIterations;
    return (int) (getCenterY() - radius);
  }

  @Override public int getMaxY() {
    int numberOfIterations = getNumberOfIterations();
    float iterationDistance = getDistance();
    float radius = iterationDistance * numberOfIterations;
    int viewHeight = getPathConfig().getViewHeight();
    float totalItemSize = getPathConfig().getFirstItemMargin() + getPathConfig().getFirstItemSize();
    return (int) (getCenterY() + radius - viewHeight + totalItemSize);
  }

  private int getNumberOfIterations() {
    int numberOfElements = getPathConfig().getNumberOfElements();
    int iteration;
    int item = 0;
    for (iteration = 1; item < numberOfElements; iteration++) {
      item += getNumberOfElementsPerIteration(item, iteration);
    }
    return iteration - 1;
  }
}

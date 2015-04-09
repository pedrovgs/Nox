package com.github.pedrovgs.nox.path;

/**
 * Simple linear Path implementation used to place NoxItem objects in a single line in
 * NoxView starting from the left side of the view. NoxItem instances in this path will have the
 * same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SimpleLinearPath extends Path {

  SimpleLinearPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public void calculate() {
    int numberOfItems = pathConfig.getNumberOfElements();
    int height = (int) ((pathConfig.getViewHeight() / 2) - (pathConfig.getFirstItemSize() / 2));

    float itemWidth = pathConfig.getFirstItemSize();
    float viewMargin = pathConfig.getFirstItemMargin();
    float x = viewMargin;

    for (int i = 0; i < numberOfItems; i++) {
      noxItemsYPositions[i] = height;
      noxItemsXPositions[i] = x;
      x += itemWidth + viewMargin;
    }
  }
}

package com.github.pedrovgs.nox.path;

/**
 * Simple linear centered Path implementation used to place NoxItem objects in a single line
 * centered in NoxView. NoxItem instances in this path will have the same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SimpleLinearCenteredPath extends SimpleLinearPath {

  SimpleLinearCenteredPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override protected float getFirstItemLeftPosition() {
    float itemSize = getPathConfig().getFirstItemSize();
    float itemMargin = getPathConfig().getFirstItemMargin();
    float center = getPathConfig().getViewWidth() / 2;
    int numberOfElements = getPathConfig().getNumberOfElements();
    return center - numberOfElements * (itemSize / 2 + itemMargin / 2);
  }
}

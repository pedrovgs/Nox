package com.github.pedrovgs.nox.path;

/**
 * Simple linear Path implementation used to place NoxItem objects in a single line centered in
 * NoxView. NoxItem instances in this path will have the same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SimpleLinearPath extends Path {

  private float[] noxItemsXPositions;
  private float[] noxItemsYPositions;

  SimpleLinearPath(PathConfig pathConfig) {
    super(pathConfig);
    int numberOfElements = pathConfig.getNumberOfElements();
    noxItemsXPositions = new float[numberOfElements];
    noxItemsYPositions = new float[numberOfElements];
  }

  @Override public float getLeftForItemAtPosition(int position) {
    return noxItemsXPositions[position];
  }

  @Override public float getTopForItemAtPosition(int position) {
    return noxItemsYPositions[position];
  }

  @Override public boolean isItemInsideView(int position) {
    float x = getLeftForItemAtPosition(position);
    float y = getTopForItemAtPosition(position);
    boolean matchesHorizontally =
        x >= 0 && (x + pathConfig.getFirstItemSize()) <= pathConfig.getViewWidth();
    boolean matchesVertically =
        y >= 0 && (y + pathConfig.getFirstItemSize() <= pathConfig.getViewHeight());
    return matchesHorizontally && matchesVertically;
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

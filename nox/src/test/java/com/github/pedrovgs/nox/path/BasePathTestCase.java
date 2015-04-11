package com.github.pedrovgs.nox.path;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class BasePathTestCase {

  private static final int ITEM_SIZE = 10;
  private static final int ITEM_MARGIN = 2;
  protected static final double DELTA = 0.1;

  public abstract Path getPath(PathConfig pathConfig);

  @Test public void shouldReturnTrueIfViewWidthIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE - 1;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewHeightIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE - 1;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfViewIsBiggerOrEqualsThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }

  protected PathConfig givenPathConfig(int numberOfElements, int viewWidth, int viewHeight,
      float itemSize, float itemMargin) {
    return new PathConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

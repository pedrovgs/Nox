package com.github.pedrovgs.nox.path;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public abstract class BasePathTestCase {

  private static final int ITEM_SIZE = 10;
  private static final int ITEM_MARGIN = 2;

  public abstract Path getPath(PathConfig pathConfig);

  @Test public void shouldReturnFalseIfAsForItemsWhenViewWidthIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE - 1;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfAsForItemsWhenViewHeightIsSmallerThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE - 1;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfAsForItemsWhenViewIsBiggerThanItemSize() {
    int viewWidth = ITEM_SIZE + ITEM_MARGIN;
    int viewHeight = ITEM_SIZE + ITEM_MARGIN;
    PathConfig pathConfig = new PathConfig(1, viewWidth, viewHeight, ITEM_SIZE, ITEM_MARGIN);

    Path path = getPath(pathConfig);

    assertTrue(path.isItemInsideView(0));
  }
}

package com.github.pedrovgs.nox.path;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class CircularPathTest extends BasePathTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Path path;

  @Before public void setUp() {
    PathConfig pathConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);
  }

  @Override public Path getPath(PathConfig pathConfig) {
    return PathFactory.getCircularPath(pathConfig);
  }

  @Test public void shouldReturnTheMiddleOfTheViewAsPositionForJustOneElement() {
    PathConfig pathConfig =
        givenPathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);

    path.calculate();

    float expectedLeft = (ANY_VIEW_WIDTH / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedLeft, path.getLeftForItemAtPosition(0), DELTA);
    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2) - (ANY_ITEM_MARGIN / 2);
    assertEquals(expectedTop, path.getTopForItemAtPosition(0), DELTA);
  }

  @Test public void shouldPositionElementsFollowingACircle() {
    PathConfig pathConfig =
        givenPathConfig(5, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);

    path.calculate();

    float centerX = ANY_VIEW_WIDTH / 2 - ANY_ITEM_SIZE / 2 - ANY_ITEM_MARGIN / 2;
    float centerY = ANY_VIEW_HEIGHT / 2 - ANY_ITEM_SIZE / 2 - ANY_ITEM_MARGIN / 2;
    float radius = ANY_ITEM_SIZE + ANY_ITEM_MARGIN;
    assertElementPositions(0, centerX, centerY, path);
    assertElementPositions(1, centerX, centerY + radius, path);
    assertElementPositions(2, centerX + radius, centerY, path);
    assertElementPositions(3, centerX, centerY - radius, path);
    assertElementPositions(4, centerX - radius, centerY, path);
  }

  private void assertElementPositions(int position, float x, float y, Path path) {
    assertEquals(x, path.getLeftForItemAtPosition(position), DELTA);
    assertEquals(y, path.getTopForItemAtPosition(position), DELTA);
  }
}

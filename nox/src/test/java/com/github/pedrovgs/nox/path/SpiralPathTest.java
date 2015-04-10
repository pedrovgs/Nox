package com.github.pedrovgs.nox.path;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class SpiralPathTest extends BasePathTestCase {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Path path;

  @Override public Path getPath(PathConfig pathConfig) {
    return PathFactory.getSpiralPath(pathConfig);
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

  @Test public void shouldConfigureElementsFollowingAnArchimedeanSpiral() {
    PathConfig pathConfig =
        givenPathConfig(10, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = getPath(pathConfig);

    path.calculate();

    assertElementsConformAnSpiral(pathConfig);
  }

  private void assertElementsConformAnSpiral(PathConfig pathConfig) {
    for (int i = 0; i < pathConfig.getNumberOfElements(); i++) {
      float expectedLeft = getExpectedLeftAtPosition(i, pathConfig);
      float expectedTop = getExpectedTopAtPosition(i, pathConfig);
      assertEquals(expectedLeft, path.getLeftForItemAtPosition(i), DELTA);
      assertEquals(expectedTop, path.getTopForItemAtPosition(i), DELTA);
    }
  }

  private float getExpectedLeftAtPosition(int i, PathConfig pathConfig) {
    float angle = pathConfig.getFirstItemSize();
    float centerX = (pathConfig.getViewWidth() / 2)
        - (pathConfig.getFirstItemSize() / 2)
        - (pathConfig.getFirstItemMargin() / 2);
    return (float) (centerX + (angle * i * Math.cos(i)));
  }

  private float getExpectedTopAtPosition(int i, PathConfig pathConfig) {
    float angle = pathConfig.getFirstItemSize();
    float centerY = (pathConfig.getViewHeight() / 2)
        - (pathConfig.getFirstItemSize() / 2)
        - (pathConfig.getFirstItemMargin() / 2);
    return (float) (centerY + (angle * i * Math.sin(i)));
  }
}

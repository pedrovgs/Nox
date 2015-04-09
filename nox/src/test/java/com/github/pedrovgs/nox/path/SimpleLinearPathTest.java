package com.github.pedrovgs.nox.path;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class SimpleLinearPathTest extends BasePathTestCase {

  private static final double DELTA = 0.1;
  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private Path path;

  @Before public void setUp() {
    PathConfig pathConfig = givenPathConfigWithNumberOfElements(1);
    path = getPath(pathConfig);
  }

  @Override public Path getPath(PathConfig pathConfig) {
    return PathFactory.getLinearPath(pathConfig);
  }

  @Test public void shouldUseSameTopForEveryElement() {
    int numberOfElements = 10;
    PathConfig pathConfig = givenPathConfigWithNumberOfElements(numberOfElements);
    path = getPath(pathConfig);

    path.calculate();

    float expectedTop = (ANY_VIEW_HEIGHT / 2) - (ANY_ITEM_SIZE / 2);
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedTop, path.getTopForItemAtPosition(i), DELTA);
    }
  }

  @Test public void shouldCalculateLeftPositionsUsingOneLinearDistributionItemSizeAndMargin() {
    int numberOfElements = 10;
    PathConfig pathConfig = givenPathConfigWithNumberOfElements(numberOfElements);
    path = getPath(pathConfig);

    path.calculate();

    float expectedLeft = ANY_ITEM_MARGIN;
    for (int i = 0; i < numberOfElements; i++) {
      assertEquals(expectedLeft, path.getLeftForItemAtPosition(i), DELTA);
      expectedLeft += ANY_ITEM_SIZE + ANY_ITEM_MARGIN;
    }
  }

  @Test public void shouldCalculateTheCorrectLeftPositionForOneElement() {
    path.calculate();

    float left = path.getLeftForItemAtPosition(0);

    assertEquals(ANY_ITEM_MARGIN, left, DELTA);
  }

  @Test public void shouldReturnTrueJustForTheElementsInsideTheView() {
    PathConfig pathConfig = givenPathConfigWithNumberOfElements(11);
    path = getPath(pathConfig);

    path.calculate();

    assertElementsAtPositionAreInsideTheView(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    assertElementsAtPositionAreOutsizeTheView(10);
  }

  private void assertElementsAtPositionAreInsideTheView(int... positions) {
    for (int position : positions) {
      assertTrue(path.isItemInsideView(position));
    }
  }

  private void assertElementsAtPositionAreOutsizeTheView(int... positions) {
    for (int position : positions) {
      assertFalse(path.isItemInsideView(position));
    }
  }

  private PathConfig givenPathConfigWithNumberOfElements(int numberOfElements) {
    return new PathConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
        ANY_ITEM_MARGIN);
  }
}

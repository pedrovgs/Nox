package com.github.pedrovgs.nox.path;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class BoundaryPathTest {

  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final float ANY_ITEM_SIZE = 8;
  private static final float ANY_ITEM_MARGIN = 2;

  private FakePath path;

  @Before public void setUp() {
    PathConfig pathConfig =
        new PathConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    path = new FakePath(pathConfig);
  }

  @Test public void shouldReturnFalseIfItemIsAtTheRightAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH + ANY_ITEM_SIZE + 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsAtTheLeftAndOutsideTheView() {
    float x = -ANY_ITEM_SIZE - 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsAboveAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = -ANY_ITEM_SIZE - 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnFalseIfItemIsBelowAndOutsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT + ANY_ITEM_SIZE + 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertFalse(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheLeftPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH - 1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheRightPartOfTheItemIsInsideTheView() {
    float x = -1;
    float y = ANY_VIEW_HEIGHT / 2;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheTopPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = ANY_VIEW_HEIGHT - 1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  @Test public void shouldReturnTrueIfTheBottomPartOfTheItemIsInsideTheView() {
    float x = ANY_VIEW_WIDTH / 2;
    float y = -1;
    givenElementsAreInPosition(x, y);

    path.calculate();

    assertTrue(path.isItemInsideView(0));
  }

  private void givenElementsAreInPosition(float x, float y) {
    path.setXPosition(x);
    path.setYPosition(y);
  }

  private class FakePath extends Path {

    private float xPosition;
    private float yPosition;

    public FakePath(PathConfig pathConfig) {
      super(pathConfig);
    }

    public void setXPosition(float xPosition) {
      this.xPosition = xPosition;
    }

    public void setYPosition(float yPosition) {
      this.yPosition = yPosition;
    }

    @Override public void calculate() {
      for (int i = 0; i < getPathConfig().getNumberOfElements(); i++) {
        setNoxItemLeftPosition(i, xPosition);
        setNoxItemTopPosition(i, yPosition);
      }
    }
  }
}

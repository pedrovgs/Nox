package com.github.pedrovgs.nox.path;

/**
 * Simple linear Path implementation used to place NoxItem objects in a single line centered in
 * NoxView. NoxItem instances in this path will have the same size.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class SimpleLinearPath extends Path {

  SimpleLinearPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public float getLeftForItemAtPosition(int position) {
    return 0;
  }

  @Override public float getRightForItemAtPosition(int position) {
    return 0;
  }

  @Override public boolean isItemInsideView(int position) {
    return false;
  }

  @Override public void calculate() {

  }
}

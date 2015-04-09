package com.github.pedrovgs.nox.path;

/**
 * Linear Path implementation used to place NoxItem objects in a single line centered in NoxView.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class LinearPath extends Path {

  LinearPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override public float getLeftForItemAtPosition(int position) {
    return 0;
  }

  @Override public float getRightForItemAtPosition(int position) {
    return 0;
  }

  @Override public void calculate() {

  }
}

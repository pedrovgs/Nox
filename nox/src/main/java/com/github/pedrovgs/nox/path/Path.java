package com.github.pedrovgs.nox.path;

/**
 * Describes where NoxView has to draw NoxItems inside the space available in NoxView.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public interface Path {

  void setNumberOfElements(int numberOfElements);

  void setSize(int width, int height);

  void setFirstItemSize(float firstItemSize);

  void setFirstItemMargin(float firstItemSize);

  float getLeftForItemAtPosition(int position);

  float getRightForItemAtPosition(int position);
}

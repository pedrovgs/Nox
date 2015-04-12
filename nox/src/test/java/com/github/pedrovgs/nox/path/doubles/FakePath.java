package com.github.pedrovgs.nox.path.doubles;

import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;

/**
 * Fake path implementation created to be used in Nox tests. This Path implementation adds two
 * methods to be able to displace NoxItem objects in a fixed x and y position.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class FakePath extends Path {

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
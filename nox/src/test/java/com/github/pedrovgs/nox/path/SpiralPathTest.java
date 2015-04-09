package com.github.pedrovgs.nox.path;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class SpiralPathTest extends BasePathTestCase {

  @Override public Path getPath(PathConfig pathConfig) {
    return PathFactory.getSpiralPath(pathConfig);
  }
}

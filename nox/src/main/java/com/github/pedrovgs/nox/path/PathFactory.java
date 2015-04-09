package com.github.pedrovgs.nox.path;

/**
 * Factory used to obtain different Path implementations.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class PathFactory {

  public static Path getLinearPath(PathConfig pathConfig) {
    return new SimpleLinearPath(pathConfig);
  }

  public static Path getLinearCenteredPath(PathConfig pathConfig) {
    return new SimpleLinearCenteredPath(pathConfig);
  }
}

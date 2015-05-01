/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pedrovgs.nox.path;

/**
 * Factory used to obtain different Path implementations. Returns Path different Path
 * implementations ready to be used. This class describes all the library possibilities related to
 * how to show NoxItem instances inside NoxView.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class PathFactory {

  public static final int LINEAR_PATH_KEY = 0;
  public static final int LINEAR_CENTERED_PATH_KEY = 1;
  public static final int CIRCULAR_PATH_KEY = 2;
  public static final int FIXED_CIRCULAR_PATH_KEY = 3;
  public static final int SPIRAL_PATH_KEY = 4;

  public static Path getLinearPath(PathConfig pathConfig) {
    return new LinearPath(pathConfig);
  }

  public static Path getLinearCenteredPath(PathConfig pathConfig) {
    return new LinearCenteredPath(pathConfig);
  }

  public static Path getSpiralPath(PathConfig pathConfig) {
    return new SpiralPath(pathConfig);
  }

  public static Path getCircularPath(PathConfig pathConfig) {
    return new CircularPath(pathConfig);
  }

  public static Path getFixedCircularPath(PathConfig pathConfig) {
    return new FixedCircularPath(pathConfig);
  }

  public static Path getPathByKey(int pathKey, PathConfig pathConfig) {
    Path path;
    switch (pathKey) {
      case LINEAR_PATH_KEY:
        path = new LinearPath(pathConfig);
        break;
      case LINEAR_CENTERED_PATH_KEY:
        path = new LinearCenteredPath(pathConfig);
        break;
      case CIRCULAR_PATH_KEY:
        path = new CircularPath(pathConfig);
        break;
      case FIXED_CIRCULAR_PATH_KEY:
        path = new FixedCircularPath(pathConfig);
        break;
      case SPIRAL_PATH_KEY:
        path = new SpiralPath(pathConfig);
        break;
      default:
        path = new LinearPath(pathConfig);
    }
    return path;
  }
}

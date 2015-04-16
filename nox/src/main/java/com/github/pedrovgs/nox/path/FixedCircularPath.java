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
 * Fixed circular Path implementation used to place NoxItem objects in a circle inside NoxView
 * starting from the center of the view. NoxItem instances in this path will have the same size and
 * the number of NoxItem elements per circle level be calculated in base of the size of the
 * element. Every circle level will be fixed with NoxItem instances.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class FixedCircularPath extends CircularPath {

  FixedCircularPath(PathConfig pathConfig) {
    super(pathConfig);
  }

  @Override protected int getNumberOfElementsPerIteration(int item, int iteration) {
    int numberOfElementsPerIteration = 0;
    float itemSize = getPathConfig().getFirstItemSize() + getPathConfig().getFirstItemMargin();
    float radius = iteration * itemSize;
    float circumferenceLong = (float) (2 * Math.PI * radius);
    if (itemSize >= circumferenceLong) {
      numberOfElementsPerIteration = Math.min(getPathConfig().getNumberOfElements(), 1);
    } else {
      numberOfElementsPerIteration = (int) (circumferenceLong / itemSize);
      numberOfElementsPerIteration =
          Math.min(numberOfElementsPerIteration, getPathConfig().getNumberOfElements() - item);
    }

    return numberOfElementsPerIteration;
  }
}

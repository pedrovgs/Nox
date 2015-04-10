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

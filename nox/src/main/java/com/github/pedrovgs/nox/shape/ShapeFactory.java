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

package com.github.pedrovgs.nox.shape;

/**
 * Provides different Shape implementations ready to be used. This class describes all the library
 * possibilities related to how to show NoxItem instances inside NoxView.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class ShapeFactory {

  public static final int LINEAR_SHAPE_KEY = 0;
  public static final int LINEAR_CENTERED_SHAPE_KEY = 1;
  public static final int CIRCULAR_SHAPE_KEY = 2;
  public static final int FIXED_CIRCULAR_SHAPE_KEY = 3;
  public static final int SPIRAL_SHAPE_KEY = 4;

  public static Shape getLinearShape(ShapeConfig shapeConfig) {
    return new LinearShape(shapeConfig);
  }

  public static Shape getLinearCenteredShape(ShapeConfig shapeConfig) {
    return new LinearCenteredShape(shapeConfig);
  }

  public static Shape getSpiralShape(ShapeConfig shapeConfig) {
    return new SpiralShape(shapeConfig);
  }

  public static Shape getCircularShape(ShapeConfig shapeConfig) {
    return new CircularShape(shapeConfig);
  }

  public static Shape getFixedCircularShape(ShapeConfig shapeConfig) {
    return new FixedCircularShape(shapeConfig);
  }

  public static Shape getShapeByKey(int shapeKey, ShapeConfig shapeConfig) {
    Shape shape;
    switch (shapeKey) {
      case LINEAR_SHAPE_KEY:
        shape = new LinearShape(shapeConfig);
        break;
      case LINEAR_CENTERED_SHAPE_KEY:
        shape = new LinearCenteredShape(shapeConfig);
        break;
      case CIRCULAR_SHAPE_KEY:
        shape = new CircularShape(shapeConfig);
        break;
      case FIXED_CIRCULAR_SHAPE_KEY:
        shape = new FixedCircularShape(shapeConfig);
        break;
      case SPIRAL_SHAPE_KEY:
        shape = new SpiralShape(shapeConfig);
        break;
      default:
        shape = new LinearShape(shapeConfig);
    }
    return shape;
  }
}

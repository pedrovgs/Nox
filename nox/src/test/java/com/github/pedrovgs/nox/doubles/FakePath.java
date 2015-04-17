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

package com.github.pedrovgs.nox.doubles;

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
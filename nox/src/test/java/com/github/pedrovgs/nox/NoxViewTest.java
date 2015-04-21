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

package com.github.pedrovgs.nox;

import android.app.Activity;
import com.github.pedrovgs.nox.doubles.FakePath;
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
@Config(emulateSdk = 18) @RunWith(RobolectricTestRunner.class) public class NoxViewTest {

  private static final int ANY_RESOURCE_ID = R.drawable.abc_ab_share_pack_holo_dark;
  private static final int ANY_MIN_X = -50;
  private static final int ANY_MAX_X = 50;
  private static final int ANY_MIN_Y = -70;
  private static final int ANY_MAX_Y = 70;
  private static final int ANY_OVER_SIZE = 10;

  private NoxView noxView;

  @Before public void setUp() {
    Activity activity = Robolectric.buildActivity(Activity.class).create().resume().get();
    noxView = new NoxView(activity);
  }

  @Test(expected = NullPointerException.class) public void shouldNotAcceptANullNoxItemListToShow() {
    noxView.showNoxItems(null);
  }

  @Test public void shouldAcceptAnEmptyListOfNoxItems() {
    noxView.showNoxItems(Collections.EMPTY_LIST);
  }

  @Test public void shouldAcceptANonEmptyListOfNoxItems() {
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem(ANY_RESOURCE_ID));

    noxView.showNoxItems(noxItems);
  }

  @Test public void shouldInitializeScrollerUsingPathInformation() {
    Path path =
        givenPathConfiguredToReturn(ANY_MIN_X, ANY_MAX_X, ANY_MIN_Y, ANY_MAX_Y, ANY_OVER_SIZE);

    noxView.setPath(path);

    assertEquals(ANY_MIN_X, noxView.getScroller().getMinX());
    assertEquals(ANY_MAX_X, noxView.getScroller().getMaxX());
    assertEquals(ANY_MIN_Y, noxView.getScroller().getMinY());
    assertEquals(ANY_MAX_Y, noxView.getScroller().getMaxY());
    assertEquals(ANY_OVER_SIZE, noxView.getScroller().getOverSize());
  }

  @Test public void shouldInvalidateViewOnNewPathConfigured() {
    noxView = Mockito.spy(noxView);
    Path path =
        givenPathConfiguredToReturn(ANY_MIN_X, ANY_MAX_X, ANY_MIN_Y, ANY_MAX_Y, ANY_OVER_SIZE);

    noxView.setPath(path);

    verify(noxView).invalidate();
  }

  @Test(expected = NullPointerException.class) public void shouldNotAcceptNullPathInstances() {
    noxView.setPath(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotAcceptANewPathWithDifferentNumberOfElementsThanThePreviousConfigured() {
    Path path = givenPathWithNumberOfElements(2);
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem(ANY_RESOURCE_ID));

    noxView.showNoxItems(noxItems);
    noxView.setPath(path);
  }

  @Test public void shouldInvalidateViewOnNewNoxItemsConfigured() {
    noxView = Mockito.spy(noxView);

    noxView.showNoxItems(Collections.EMPTY_LIST);

    verify(noxView).invalidate();
  }

  private Path givenPathWithNumberOfElements(int numberOfElements) {
    PathConfig pathConfig = new PathConfig(numberOfElements, 0, 0, 0, 0);
    return new FakePath(pathConfig);
  }

  private Path givenPathConfiguredToReturn(int minX, int maxX, int minY, int maxY, int overSize) {
    PathConfig pathConfig = new PathConfig(1, 0, 0, 0, 0);
    FakePath path = new FakePath(pathConfig);
    path.setBoundaries(minX, maxX, minY, maxY, overSize);
    return path;
  }
}

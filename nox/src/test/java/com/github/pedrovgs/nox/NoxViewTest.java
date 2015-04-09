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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
@Config(emulateSdk = 18) @RunWith(RobolectricTestRunner.class) public class NoxViewTest {

  private static final int ANY_RESOURCE_ID = R.drawable.abc_ab_share_pack_holo_dark;

  private NoxView noxView;

  @Before public void setUp() {
    Activity activity = Robolectric.buildActivity(Activity.class).create().resume().get();
    noxView = new NoxView(activity);
  }

  @Test(expected = NullPointerException.class) public void shouldNotAcceptANullNoxItemListToShow() {
    noxView.showNoxItems(null);
  }

  @Test public void shouldAcceptAnEmptyListOfNoxItems() {
    noxView.showNoxItems(new ArrayList<NoxItem>());
  }

  @Test public void shouldAcceptANonEmptyListOfNoxItems() {
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem(ANY_RESOURCE_ID));

    noxView.showNoxItems(noxItems);
  }
}

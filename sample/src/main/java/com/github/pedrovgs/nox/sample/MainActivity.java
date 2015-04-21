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

package com.github.pedrovgs.nox.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample activity created to show how to use NoxView with a List of NoxItem instances.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class MainActivity extends ActionBarActivity {

  private NoxView noxView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    configureNoxView();
  }

  private void configureNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    configurePath();
    configureNoxItems();
  }

  private void configurePath() {
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    int width = metrics.widthPixels;
    int height = metrics.heightPixels;
    float itemSize = getResources().getDimension(R.dimen.nox_item_size_1);
    float itemMargin = getResources().getDimension(R.dimen.nox_item_margin_1);
    PathConfig pathConfig = new PathConfig(2, width, height, itemSize, itemMargin);
    Path verticalLinearPath = new VerticalLinearPath(pathConfig);
    noxView.setPath(verticalLinearPath);
  }

  private void configureNoxItems() {
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem("http://www.upaf.com/wp-content/uploads/2014/11/user_icon.png"));
    noxItems.add(new NoxItem(R.drawable.ic_apps));
    noxView.showNoxItems(noxItems);
  }
}

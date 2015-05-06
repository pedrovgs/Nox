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

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import com.github.pedrovgs.nox.OnNoxItemClickListener;
import com.github.pedrovgs.nox.shape.Shape;
import com.github.pedrovgs.nox.shape.ShapeConfig;
import com.github.pedrovgs.nox.shape.ShapeFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class AppsActivity extends ActionBarActivity {

  private static final String LOGTAG = "AppsActivity";

  private NoxView noxView;
  private List<NoxItem> apps;
  private List<String> packageNames;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_apps);
    initializeNoxView();
  }

  private void initializeNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    packageNames = new ArrayList<String>();
    apps = getApps();
    noxView.showNoxItems(apps);
    noxView.setOnNoxItemClickListener(new OnNoxItemClickListener() {
      @Override public void onNoxItemClicked(int position, NoxItem noxItem) {
        Log.d(LOGTAG, "NoxItem clicked at position " + position);
        String packageName = packageNames.get(position);
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
          startActivity(intent);
        }
      }
    });
  }

  private List<ApplicationInfo> getInstalledApps() {
    return getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
  }

  private List<NoxItem> getApps() {
    List<NoxItem> apps = new ArrayList<NoxItem>();
    for (ApplicationInfo app : getInstalledApps()) {
      String packageName = app.packageName;
      if (app.icon != 0) {
        packageNames.add(packageName);
        Uri uri = Uri.parse("android.resource://" + packageName + "/" + app.icon);
        apps.add(new NoxItem(uri.toString()));
      }
    }
    return apps;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_shape, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Shape newShape;
    ShapeConfig shapeConfig = getShapeConfig();
    switch (id) {
      case R.id.linear_shape_option:
        newShape = ShapeFactory.getLinearShape(shapeConfig);
        noxView.setShape(newShape);
        break;
      case R.id.linear_centered_shape_option:
        newShape = ShapeFactory.getLinearCenteredShape(shapeConfig);
        noxView.setShape(newShape);
        break;
      case R.id.circular_shape_option:
        newShape = ShapeFactory.getCircularShape(shapeConfig);
        noxView.setShape(newShape);
        break;
      case R.id.fixed_circular_shape_option:
        newShape = ShapeFactory.getFixedCircularShape(shapeConfig);
        noxView.setShape(newShape);
        break;
      default:
        newShape = ShapeFactory.getSpiralShape(shapeConfig);
        noxView.setShape(newShape);
    }
    return super.onOptionsItemSelected(item);
  }

  private ShapeConfig getShapeConfig() {
    int numberOfElements = apps.size();
    int viewWidth = noxView.getWidth();
    int viewHeight = noxView.getHeight();
    float itemSize = getResources().getDimension(R.dimen.default_nox_item_size);
    float itemMargin = getResources().getDimension(R.dimen.apps_activity_nox_item_margin);
    return new ShapeConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

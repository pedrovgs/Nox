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

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import com.github.pedrovgs.nox.path.PathFactory;
import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends ActionBarActivity {

  private NoxView noxView;
  private List<NoxItem> apps;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_apps);
    initializeNoxView();
  }

  private void initializeNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    apps = getApps();
    noxView.showNoxItems(apps);
  }

  private List<NoxItem> getApps() {
    List<NoxItem> apps = new ArrayList<NoxItem>();
    for (ApplicationInfo app : getInstalledApps()) {
      String packageName = app.packageName;
      if (app.icon != 0) {
        Uri uri = Uri.parse("android.resource://" + packageName + "/" + app.icon);
        apps.add(new NoxItem(uri.toString()));
      }
    }
    return apps;
  }

  private List<ApplicationInfo> getInstalledApps() {
    return getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_paths, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Path newPath;
    PathConfig pathConfig = getPathConfig();
    switch (id) {
      case R.id.linear_path_option:
        newPath = PathFactory.getLinearPath(pathConfig);
        noxView.setPath(newPath);
        break;
      case R.id.linear_centered_path_option:
        newPath = PathFactory.getLinearCenteredPath(pathConfig);
        noxView.setPath(newPath);
        break;
      case R.id.circular_path_option:
        newPath = PathFactory.getCircularPath(pathConfig);
        noxView.setPath(newPath);
        break;
      case R.id.fixed_circular_path_option:
        newPath = PathFactory.getFixedCircularPath(pathConfig);
        noxView.setPath(newPath);
        break;
      default:
        newPath = PathFactory.getSpiralPath(pathConfig);
        noxView.setPath(newPath);
    }
    return super.onOptionsItemSelected(item);
  }

  private PathConfig getPathConfig() {
    int numberOfElements = apps.size();
    int viewWidth = noxView.getWidth();
    int viewHeight = noxView.getHeight();
    float itemSize = getResources().getDimension(R.dimen.default_nox_item_size);
    float itemMargin = getResources().getDimension(R.dimen.apps_activity_nox_item_margin);
    return new PathConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

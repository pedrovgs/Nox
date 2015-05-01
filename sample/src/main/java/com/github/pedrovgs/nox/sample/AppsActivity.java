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
import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends ActionBarActivity {

  private NoxView noxView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_apps);
    initializeNoxView();
  }

  private void initializeNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    List<NoxItem> apps = getApps();
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
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

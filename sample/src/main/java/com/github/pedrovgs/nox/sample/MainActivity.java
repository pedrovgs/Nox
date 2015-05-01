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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import com.github.pedrovgs.nox.OnNoxItemClickListener;
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

  private static final String LOGTAG = "MainActivity";

  private NoxView noxView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    configureNoxView();
  }

  private void configureNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    noxView.post(new Runnable() {
      @Override public void run() {
        int numberOfItems = configureNoxItems();
        configurePath(numberOfItems);
        configureClickListeners();
      }
    });
  }

  private int configureNoxItems() {
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem(R.drawable.ic_contacts));
    noxItems.add(new NoxItem(R.drawable.ic_apps));
    noxView.showNoxItems(noxItems);
    return noxItems.size();
  }

  private void configurePath(int numberOfItems) {
    int width = noxView.getWidth();
    int height = noxView.getHeight();
    float itemSize = getResources().getDimension(R.dimen.nox_item_size);
    float itemMargin = getResources().getDimension(R.dimen.main_activity_nox_item_margin);
    PathConfig pathConfig = new PathConfig(numberOfItems, width, height, itemSize, itemMargin);
    Path verticalLinearPath = new VerticalLinearPath(pathConfig);
    noxView.setPath(verticalLinearPath);
  }

  private void configureClickListeners() {
    noxView.setOnItemClickListener(new OnNoxItemClickListener() {
      @Override public void onNoxItemClicked(int position, NoxItem noxItem) {
        Log.e(LOGTAG, "Item click not handled at position " + position);
        switch (position) {
          case 0:
            openActivity(ContactsActivity.class);
            break;
          case 1:
            openActivity(AppsActivity.class);
            break;
          default:
        }
      }
    });
  }

  private void openActivity(Class clazz) {
    Intent intent = new Intent(this, clazz);
    startActivity(intent);
  }
}

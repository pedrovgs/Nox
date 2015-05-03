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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import com.github.pedrovgs.nox.OnNoxItemClickListener;
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import com.github.pedrovgs.nox.path.PathFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class ContactsActivity extends ActionBarActivity {

  private static final int INITIAL_CONTACTS = 80;
  private NoxView noxView;
  private Button addContact;
  private Button removeContact;
  private int lastContactId;

  private List<NoxItem> contacts;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts);
    configureNoxView();
    configureButtons();
  }

  private void configureButtons() {
    addContact = (Button) findViewById(R.id.add_contact);
    addContact.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        lastContactId++;
        String newContactUrl = getUrlForContact(lastContactId);
        contacts.add(new NoxItem(newContactUrl));
        noxView.notifyDataSetChanged();
      }
    });

    removeContact = (Button) findViewById(R.id.remove_contact);
    removeContact.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        int lastContact = contacts.size() - 1;
        contacts.remove(lastContact);
        lastContactId--;
        noxView.notifyDataSetChanged();
      }
    });
  }

  private void configureNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    List<NoxItem> contacts = getContacts();
    noxView.showNoxItems(contacts);
    noxView.setOnNoxItemClickListener(new OnNoxItemClickListener() {
      @Override public void onNoxItemClicked(int position, NoxItem noxItem) {
        String contactPhoto = noxItem.getUrl();
        Intent intent = ContactPhotoActivity.getIntent(ContactsActivity.this, contactPhoto);
        startActivity(intent);
      }
    });
  }

  private List<NoxItem> getContacts() {
    contacts = new ArrayList<NoxItem>();
    for (int i = 0; i < INITIAL_CONTACTS; i++) {
      String contactUrl = getUrlForContact(i);
      NoxItem noxItem = new NoxItem(contactUrl);
      contacts.add(noxItem);
      lastContactId = i;
    }
    return contacts;
  }

  private String getUrlForContact(int i) {
    String source = i % 2 == 0 ? "women" : "men";
    return "http://api.randomuser.me/portraits/thumb/" + source + "/" + i / 2 + ".jpg";
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
    int numberOfElements = contacts.size();
    int viewWidth = noxView.getWidth();
    int viewHeight = noxView.getHeight();
    float itemSize = getResources().getDimension(R.dimen.default_nox_item_size);
    float itemMargin = getResources().getDimension(R.dimen.apps_activity_nox_item_margin);
    return new PathConfig(numberOfElements, viewWidth, viewHeight, itemSize, itemMargin);
  }
}

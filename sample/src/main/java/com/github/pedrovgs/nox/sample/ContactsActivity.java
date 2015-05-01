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
import com.github.pedrovgs.nox.NoxItem;
import com.github.pedrovgs.nox.NoxView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
public class ContactsActivity extends ActionBarActivity {

  private NoxView noxView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts);
    configureNoxView();
  }

  private void configureNoxView() {
    noxView = (NoxView) findViewById(R.id.nox_view);
    List<NoxItem> contacts = getContacts();
    noxView.showNoxItems(contacts);
  }

  private List<NoxItem> getContacts() {
    List<NoxItem> contacts = new ArrayList<NoxItem>();
    for (int i = 0; i < 50; i++) {
      String contactUrl = "http://api.randomuser.me/portraits/thumb/women/" + i + ".jpg";
      NoxItem noxItem = new NoxItem(contactUrl);
      contacts.add(noxItem);
    }
    return contacts;
  }
}

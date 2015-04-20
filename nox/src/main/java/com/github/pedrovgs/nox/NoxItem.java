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

/**
 * Presentation model used by NoxView to render UI elements based on the URL or the resource id
 * configured in this class. NoxView will receive a list of this objects to be painted in the UI.
 * The placeholder id will be used to load a resource if the NoxView doesn't receive a placeholder
 * id by xml configuration or is programmatically configured.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class NoxItem {

  private final String url;
  private final Integer resourceId;
  private final Integer placeholderId;

  public NoxItem(String url) {
    validateUrl(url);
    this.url = url;
    this.resourceId = null;
    this.placeholderId = null;
  }

  public NoxItem(int resourceId) {
    this.url = null;
    this.resourceId = resourceId;
    this.placeholderId = null;
  }

  public NoxItem(String url, int placeholderId) {
    validateUrl(url);
    this.url = url;
    this.placeholderId = placeholderId;
    this.resourceId = null;
  }

  public boolean hasUrl() {
    return url != null;
  }

  public boolean hasResourceId() {
    return resourceId != null;
  }

  public boolean hasPlaceholder() {
    return placeholderId != null;
  }

  public String getUrl() {
    return url;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public Integer getPlaceholderId() {
    return placeholderId;
  }

  private void validateUrl(String url) {
    if (url == null) {
      throw new NullPointerException("The url String used to create a NoxItem can't be null");
    }
  }
}

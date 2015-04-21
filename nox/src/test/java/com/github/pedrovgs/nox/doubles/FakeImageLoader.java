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

package com.github.pedrovgs.nox.doubles;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.github.pedrovgs.nox.imageloader.ImageLoader;

/**
 * ImageLoader test double created to simulate an asynchronous image loading in test time.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class FakeImageLoader implements ImageLoader {

  private String url;
  private Integer resourceId;
  private Integer placeholderId;
  private Listener listener;
  private boolean loadOnDemand;

  public FakeImageLoader() {

  }

  public FakeImageLoader(boolean loadOnDemand) {
    this.loadOnDemand = loadOnDemand;
  }

  @Override public ImageLoader load(String url) {
    this.url = url;
    return this;
  }

  @Override public ImageLoader load(Integer resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  @Override public ImageLoader withPlaceholder(Integer placeholderId) {
    this.placeholderId = placeholderId;
    return this;
  }

  @Override public ImageLoader useCircularTransformation() {
    return this;
  }

  @Override public ImageLoader size(int size) {
    return this;
  }

  @Override public void notify(Listener listener) {
    this.listener = listener;
    if (loadOnDemand) {
      return;
    }
    Drawable fakeDrawable = placeholderId != null ? new ColorDrawable() : null;
    listener.onPlaceholderLoaded(fakeDrawable);

    boolean hasResourceToLoad = url != null || resourceId != null;
    Bitmap fakeBitmap = hasResourceToLoad ? Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8) : null;
    listener.onImageLoaded(fakeBitmap);
  }

  @Override public void pause() {

  }

  @Override public void resume() {

  }

  public void forceLoad() {
    loadOnDemand = false;
    notify(listener);
  }

  public void forceError() {
    listener.onError();
  }
}

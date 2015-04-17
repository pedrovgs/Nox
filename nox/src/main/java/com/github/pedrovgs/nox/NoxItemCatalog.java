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

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.github.pedrovgs.nox.imageloader.ImageLoader;
import java.util.List;
import java.util.Observable;

/**
 * Processes NoxItem instances to download the images associated to a set of NoxItem instances
 * asynchronously. Notifies when a new bitmap is ready to be used to the class clients.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class NoxItemCatalog extends Observable {

  private final List<NoxItem> noxItems;
  private final int noxItemSize;
  private final ImageLoader imageLoader;
  private final Bitmap[] bitmaps;
  private final Drawable[] placeholders;
  private final boolean[] resourceNotFound;
  private final boolean[] loading;
  private Drawable placeholder;

  NoxItemCatalog(List<NoxItem> noxItems, int noxItemSize, ImageLoader imageLoader) {
    validateNoxItems(noxItems);
    this.noxItems = noxItems;
    this.noxItemSize = noxItemSize;
    this.imageLoader = imageLoader;
    this.bitmaps = new Bitmap[noxItems.size()];
    this.placeholders = new Drawable[noxItems.size()];
    this.resourceNotFound = new boolean[noxItems.size()];
    this.loading = new boolean[noxItems.size()];
  }

  int size() {
    return noxItems.size();
  }

  boolean isBitmapReady(int position) {
    return bitmaps[position] != null;
  }

  boolean isPlaceholderReady(int position) {
    return placeholders[position] != null || placeholder != null;
  }

  boolean isResourceAvailable(int position) {
    return !resourceNotFound[position];
  }

  Bitmap getBitmap(int position) {
    return bitmaps[position];
  }

  Drawable getPlaceholder(int position) {
    Drawable placeholder = placeholders[position];
    if (placeholder == null) {
      placeholder = this.placeholder;
    }
    return placeholder;
  }

  void setPlaceholder(Drawable placeholder) {
    this.placeholder = placeholder;
  }

  void load(int position) {
    if (!isBitmapReady(position) && isResourceAvailable(position) && !isDownloading(position)) {
      loading[position] = true;
      NoxItem noxItem = noxItems.get(position);
      loadNoxItem(position, noxItem);
    }
  }

  void resume() {
    imageLoader.resume();
  }

  void pause() {
    imageLoader.pause();
  }

  private void loadNoxItem(final int position, NoxItem noxItem) {
    imageLoader.load(noxItem.getResourceId())
        .load(noxItem.getUrl())
        .withPlaceholder(noxItem.getPlaceholderId())
        .size(noxItemSize)
        .useCircularTransformation()
        .notify(getImageLoaderListener(position));
  }

  private ImageLoader.Listener getImageLoaderListener(final int position) {
    return new ImageLoader.Listener() {
      @Override public void onPlaceholderLoaded(Drawable placeholder) {
        placeholders[position] = placeholder;
        notifyNoxItemReady(position);
      }

      @Override public void onImageLoaded(Bitmap image) {
        bitmaps[position] = image;
        notifyNoxItemReady(position);
        loading[position] = false;
      }

      @Override public void onError() {
        loading[position] = false;
      }

      @Override public void onResourceNotFound() {
        resourceNotFound[position] = true;
      }
    };
  }

  private boolean isDownloading(int position) {
    return loading[position];
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
  }

  private void notifyNoxItemReady(int position) {
    setChanged();
    notifyObservers(position);
  }
}

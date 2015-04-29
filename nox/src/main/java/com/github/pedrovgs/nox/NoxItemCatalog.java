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
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Observable;

/**
 * Processes NoxItem instances to download the images associated to a list of NoxItem instances
 * asynchronously. This object notifies observer previously added when the resource associated to
 * the NoxItem is ready to be used.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class NoxItemCatalog extends Observable {

  private final List<NoxItem> noxItems;
  private final int noxItemSize;
  private final ImageLoader imageLoader;
  private final WeakReference<Bitmap>[] bitmaps;
  private final WeakReference<Drawable>[] drawables;
  private final WeakReference<Drawable>[] placeholders;
  private final boolean[] loading;

  private ImageLoader.Listener[] listeners;
  private Drawable placeholder;

  NoxItemCatalog(List<NoxItem> noxItems, int noxItemSize, ImageLoader imageLoader) {
    validateNoxItems(noxItems);
    this.noxItems = noxItems;
    this.noxItemSize = noxItemSize;
    this.imageLoader = imageLoader;
    this.bitmaps = new WeakReference[noxItems.size()];
    this.drawables = new WeakReference[noxItems.size()];
    this.placeholders = new WeakReference[noxItems.size()];
    this.loading = new boolean[noxItems.size()];
    this.listeners = new ImageLoader.Listener[noxItems.size()];
  }

  /**
   * Returns the NoxItem associated to a given position passed as parameter.
   */
  NoxItem getNoxItem(int position) {
    return noxItems.get(position);
  }

  /**
   * Returns the number of nox items to be loaded.
   */
  int size() {
    return noxItems.size();
  }

  /**
   * Returns true if the bitmap associated to the NoxItem given a position has been loaded.
   */
  boolean isBitmapReady(int position) {
    return bitmaps[position] != null && bitmaps[position].get() != null;
  }

  /**
   * Returns true if the drawable associated to the NoxItem given a position has been loaded.
   */
  boolean isDrawableReady(int position) {
    return drawables[position] != null && drawables[position] != null;
  }

  /**
   * Returns true if the placeholder associated to the NoxItem given a position has been
   * loaded.
   */
  boolean isPlaceholderReady(int position) {
    return (placeholders[position] != null && placeholders[position].get() != null)
        || placeholder != null;
  }

  /**
   * Returns the bitmap associated to a NoxItem instance given a position or null if the resource
   * wasn't downloaded.
   */
  Bitmap getBitmap(int position) {
    return bitmaps[position] != null ? bitmaps[position].get() : null;
  }

  /**
   * Returns the drawable associated to a NoxItem instance given a position or null if the resource
   * wasn't downloaded.
   */
  Drawable getDrawable(int position) {
    return drawables[position] != null ? drawables[position].get() : null;
  }

  /**
   * Returns the placeholder associated to a NoxItem instance given a position or null if the
   * resource wasn't downloaded or previously configured.
   */
  Drawable getPlaceholder(int position) {
    Drawable placeholder = null;
    if (placeholders[position] != null) {
      placeholder = placeholders[position].get();
    }
    if (placeholder == null) {
      placeholder = this.placeholder;
    }
    return placeholder;
  }

  /**
   * Configures a placeholder to be used if the NoxItem has no placeholder configured.
   */
  void setPlaceholder(Drawable placeholder) {
    this.placeholder = placeholder;
  }

  /**
   * Given a position associated to a NoxItem starts the NoxItem resources download. This load will
   * download the resources associated to the NoxItem only if wasn't previously downloaded or the
   * download is not being performed.
   */
  void load(int position) {
    if (!isBitmapReady(position) && !isDownloading(position)) {
      loading[position] = true;
      NoxItem noxItem = noxItems.get(position);
      loadNoxItem(position, noxItem);
    }
  }

  /**
   * Resumes the NoxItem downloads if was previously paused.
   */
  void resume() {
    imageLoader.resume();
  }

  /**
   * Pauses the NoxItem downloads.
   */
  void pause() {
    imageLoader.pause();
  }

  /**
   * Called by NoxItemCatalogImageLoaderListener when a NoxItem is ready to be used.
   */
  void notifyNoxItemReady(int position) {
    setChanged();
    notifyObservers(position);
  }

  /**
   * Configures the bitmap associated to a NoxItem given a position.
   */
  void setBitmap(int position, Bitmap image) {
    bitmaps[position] = new WeakReference<Bitmap>(image);
  }

  /**
   * Configures the drawable associated to a NoxItem given a positin.
   */
  void setDrawable(int position, Drawable drawable) {
    drawables[position] = new WeakReference<Drawable>(drawable);
  }

  /**
   * Indicates a NoxItem is being loaded given a position.
   */
  void setLoading(int position, boolean isLoading) {
    loading[position] = isLoading;
  }

  /**
   * Configures the placeholder associated to a NoxItem given a position.
   */
  void setPlaceholder(int position, Drawable placeholder) {
    placeholders[position] = new WeakReference<Drawable>(placeholder);
  }

  /**
   * Starts the resource download given a NoxItem instance and a given position.
   */
  private void loadNoxItem(final int position, NoxItem noxItem) {
    imageLoader.load(noxItem.getResourceId())
        .load(noxItem.getUrl())
        .withPlaceholder(noxItem.getPlaceholderId())
        .size(noxItemSize)
        .useCircularTransformation()
        .notify(getImageLoaderListener(position));
  }

  /**
   * Returns the ImageLoader.Listener associated to a NoxItem given a position. If the
   * ImageLoader.Listener wasn't previously created, creates a new instance.
   */
  private ImageLoader.Listener getImageLoaderListener(final int position) {
    if (listeners[position] == null) {
      listeners[position] = new NoxItemCatalogImageLoaderListener(position, this);
    }
    return listeners[position];
  }

  /**
   * Returns true if a NoxItem is being downloaded given a NoxItem position.
   */
  private boolean isDownloading(int position) {
    return loading[position];
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
  }
}

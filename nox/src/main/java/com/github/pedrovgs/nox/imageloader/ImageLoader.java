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

package com.github.pedrovgs.nox.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Download images in background and notifies the result using a listener mechanism. This
 * abstraction is needed to hide the usage of concrete image management libraries and to improve
 * the project testability. This ImageLoader interface offers a fluent API similar to the most used
 * Android libraries. The resource will be downloaded asynchronously.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public interface ImageLoader {

  /**
   * Configures an url to be downloaded.
   */
  ImageLoader load(String url);

  /**
   * Configures a resource id to be loaded.
   */
  ImageLoader load(Integer resourceId);

  /**
   * Loads a placeholder using a resource id to be shown while the external resource is being
   * downloaded.
   */
  ImageLoader withPlaceholder(Integer placeholderId);

  /**
   * Applies a circular transformation to transform the source bitmap into a circular one.
   */
  ImageLoader useCircularTransformation();

  /**
   * Changes the external resource size once it downloaded and before to notify the listener.
   */
  ImageLoader size(int size);

  /**
   * Configures a listener where the ImageLoader will notify once the resource be loaded. This
   * method has to be called to start the resource download. The listener used can't be null.
   */
  void notify(Listener listener);

  /**
   * Pauses all the resource downloads associated to this library.
   */
  void pause();

  /**
   * Resumes all the resource downloads associated to this library.
   */
  void resume();

  /**
   * Declares some methods which will be called during the resource download process implemented by
   * the ImageLoader. Use this interface to be notified when the placeholder and the final resource
   * be ready to be used.
   */
  interface Listener {

    void onPlaceholderLoaded(Drawable placeholder);

    void onImageLoaded(Bitmap image);

    void onError();
  }
}

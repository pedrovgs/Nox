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
 * the project testability.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public interface ImageLoader {

  ImageLoader load(String url);

  ImageLoader load(Integer resourceId);

  ImageLoader withPlaceholder(Integer placeholderId);

  ImageLoader useCircularTransformation();

  ImageLoader size(int size);

  void notify(Listener listener);

  void pause();

  void resume();

  public interface Listener {

    void onPlaceholderLoaded(Drawable placeholder);

    void onImageLoaded(Bitmap image);

    void onError();

    void onResourceNotFound();
  }
}

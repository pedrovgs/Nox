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

import android.content.Context;

/**
 * ImageLoader implementation based on Glide, a third party library implemented by
 * https://github.com/bumptech and recommended by Google. Official repository:
 * https://github.com/bumptech/glide
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class GlideImageLoader implements ImageLoader {

  private final Context context;

  public GlideImageLoader(Context context) {
    this.context = context;
  }

  @Override public ImageLoader load(String url) {
    return null;
  }

  @Override public ImageLoader load(Integer resourceId) {
    return null;
  }

  @Override public ImageLoader withPlaceholder(Integer resourceId) {
    return null;
  }

  @Override public ImageLoader useCircularTransformation() {
    return null;
  }

  @Override public ImageLoader notify(Listener listener) {
    return null;
  }
}

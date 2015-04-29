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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
class ListenerTarget implements Target {

  private ImageLoader.Listener listener;

  ListenerTarget(ImageLoader.Listener listener) {
    this.listener = listener;
  }

  public ImageLoader.Listener getListener() {
    return listener;
  }

  @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
    listener.onImageLoaded(bitmap);
  }

  @Override public void onBitmapFailed(Drawable errorDrawable) {
    listener.onError();
  }

  @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
    listener.onPlaceholderLoaded(placeHolderDrawable);
  }

  public void onDrawableLoad(Drawable drawable){
    listener.onDrawableLoaded(drawable);
  }
}

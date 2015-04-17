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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.pedrovgs.nox.transformation.CircularTransformation;

/**
 * ImageLoader implementation based on Glide, a third party library implemented by
 * https://github.com/bumptech and recommended by Google. Official repository:
 * https://github.com/bumptech/glide
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class GlideImageLoader implements ImageLoader {

  private final Context context;

  private String url;
  private Integer resourceId;
  private Integer placeholderId;
  private boolean useCircularTransformation;
  private Listener listener;

  public GlideImageLoader(Context context) {
    this.context = context;
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
    this.useCircularTransformation = true;
    return this;
  }

  @Override public void notify(Listener listener) {
    validateListener(listener);
    this.listener = listener;
    loadImage();
  }

  @Override public void pause() {
    Glide.with(context).pauseRequests();
  }

  @Override public void resume() {
    Glide.with(context).resumeRequests();
  }

  private Transformation[] prepareTransformations() {
    Transformation[] transformations;
    if (useCircularTransformation) {
      transformations = new Transformation[1];
      transformations[0] = new CircularTransformation(context);
    } else {
      transformations = new Transformation[0];
    }
    return transformations;
  }

  private Target getLinearTarget(final Listener listener) {
    return new SimpleTarget<Bitmap>() {
      @Override
      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        listener.onImageLoaded(resource);
      }

      @Override public void onLoadStarted(Drawable placeholder) {
        listener.onPlaceholderLoaded(placeholder);
      }
    };
  }

  private void validateListener(Listener listener) {
    if (listener == null) {
      throw new NullPointerException(
          "You can't pass a null instance of GlideImageLoader.Listener.");
    }
  }

  private void loadImage() {
    Transformation[] transformations = prepareTransformations();
    boolean hasUrl = url != null;
    boolean hasResourceId = resourceId != null;
    Target listenerTarget = getLinearTarget(listener);
    if (hasUrl) {
      Glide.with(context)
          .load(url)
          .asBitmap()
          .placeholder(placeholderId)
          .transform(transformations)
          .into(listenerTarget);
    } else if (hasResourceId) {
      Glide.with(context)
          .load(resourceId)
          .asBitmap()
          .placeholder(placeholderId)
          .transform(transformations)
          .into(listenerTarget);
    } else {
      throw new IllegalArgumentException(
          "Review your request, you are trying to load an image without a url or a resource id.");
    }
  }
}

package com.github.pedrovgs.nox.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.github.pedrovgs.nox.imageloader.transformation.CircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import java.util.LinkedList;
import java.util.List;

/**
 * ImageLoader implementation based on Picasso, a third party library implemented by
 * https://github.com/square. Official repository: https://github.com/square/picasso
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class PicassoImageLoader implements ImageLoader {

  private static final String PICASSO_IMAGE_LOADER_TAG = "PicassoImageLoaderTag";

  private final Context context;

  private String url;
  private Integer resourceId;
  private Integer placeholderId;
  private boolean useCircularTransformation;
  private int size;
  private Listener listener;

  PicassoImageLoader(Context context) {
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

  @Override public ImageLoader size(int size) {
    this.size = size;
    return this;
  }

  @Override public void notify(Listener listener) {
    validateListener(listener);
    this.listener = listener;
    loadImage();
  }

  private void loadImage() {
    List<Transformation> transformations = prepareTransformations();
    boolean hasUrl = url != null;
    boolean hasResourceId = resourceId != null;
    Target listenerTarget = getLinearTarget(listener);
    if (hasUrl) {
      RequestCreator bitmapRequest = Picasso.with(context).load(url);
      applyPlaceholder(bitmapRequest).resize(size, size)
          .transform(transformations)
          .into(listenerTarget);
    } else if (hasResourceId) {
      RequestCreator bitmapRequest = Picasso.with(context).load(resourceId);
      applyPlaceholder(bitmapRequest).resize(size, size)
          .transform(transformations)
          .into(listenerTarget);
    } else {
      throw new IllegalArgumentException(
          "Review your request, you are trying to load an image without a url or a resource id.");
    }
  }

  private Target getLinearTarget(final Listener listener) {
    return new Target() {
      @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        listener.onImageLoaded(bitmap);
      }

      @Override public void onBitmapFailed(Drawable errorDrawable) {
        listener.onError();
      }

      @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
        listener.onPlaceholderLoaded(placeHolderDrawable);
      }
    };
  }

  private RequestCreator applyPlaceholder(RequestCreator bitmapRequest) {
    if (placeholderId != null) {
      bitmapRequest.placeholder(placeholderId);
    }
    return bitmapRequest;
  }

  private List<Transformation> prepareTransformations() {
    List<Transformation> transformations = new LinkedList<Transformation>();
    if (useCircularTransformation) {
      transformations.add(new CircleTransformation());
    }
    return transformations;
  }

  @Override public void pause() {
    Picasso.with(context).pauseTag(PICASSO_IMAGE_LOADER_TAG);
  }

  @Override public void resume() {
    Picasso.with(context).resumeTag(PICASSO_IMAGE_LOADER_TAG);
  }

  private void validateListener(Listener listener) {
    if (listener == null) {
      throw new NullPointerException(
          "You can't pass a null instance of GlideImageLoader.Listener.");
    }
  }
}

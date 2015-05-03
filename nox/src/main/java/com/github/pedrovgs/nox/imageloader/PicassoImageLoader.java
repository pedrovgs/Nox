package com.github.pedrovgs.nox.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.github.pedrovgs.nox.imageloader.transformation.CircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * ImageLoader implementation based on Picasso, a third party library implemented by
 * https://github.com/square. Official repository: https://github.com/square/picasso.
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
  private final Map<Listener, ListenerTarget> targets;
  private List<Transformation> transformations;

  PicassoImageLoader(Context context) {
    this.context = context;
    this.targets = new WeakHashMap<Listener, ListenerTarget>();
    resume();
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

  @Override public ImageLoader useCircularTransformation(boolean useCircularTransformation) {
    this.useCircularTransformation = useCircularTransformation;
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

  @Override public void pause() {
    Picasso.with(context).pauseTag(PICASSO_IMAGE_LOADER_TAG);
  }

  @Override public void resume() {
    Picasso.with(context).resumeTag(PICASSO_IMAGE_LOADER_TAG);
  }

  @Override public void cancelPendingRequests() {
    Picasso.with(context).cancelTag(PICASSO_IMAGE_LOADER_TAG);
  }

  /**
   * Uses the configuration previously applied using this ImageLoader builder to download a
   * resource asynchronously and notify the result to the listener.
   */
  private void loadImage() {
    List<Transformation> transformations = getTransformations();
    boolean hasUrl = url != null;
    boolean hasResourceId = resourceId != null;
    boolean hasPlaceholder = placeholderId != null;
    ListenerTarget listenerTarget = getLinearTarget(listener);
    if (hasUrl) {
      RequestCreator bitmapRequest = Picasso.with(context).load(url).tag(PICASSO_IMAGE_LOADER_TAG);
      applyPlaceholder(bitmapRequest).resize(size, size)
          .transform(transformations)
          .into(listenerTarget);
    } else if (hasResourceId || hasPlaceholder) {
      Resources resources = context.getResources();
      Drawable placeholder = null;
      Drawable drawable = null;
      if (hasPlaceholder) {
        placeholder = resources.getDrawable(placeholderId);
        listenerTarget.onPrepareLoad(placeholder);
      }
      if (hasResourceId) {
        drawable = resources.getDrawable(resourceId);
        listenerTarget.onDrawableLoad(drawable);
      }
    } else {
      throw new IllegalArgumentException(
          "Review your request, you are trying to load an image without a url or a resource id.");
    }
  }

  /**
   * Given a listener passed as argument creates or returns a lazy instance of a Picasso Target.
   * This implementation is needed because Picasso doesn't keep a strong reference to the target
   * passed as parameter. Without this method Picasso looses the reference to the target and never
   * notifies when the resource has been downloaded.
   *
   * Listener and Target instances are going to be stored into a WeakHashMap to avoid a memory leak
   * when ImageLoader client code is garbage collected.
   */
  private ListenerTarget getLinearTarget(final Listener listener) {
    ListenerTarget target = targets.get(listener);
    if (target == null) {
      target = new ListenerTarget(listener);
      targets.put(listener, target);
    }
    return target;
  }

  private RequestCreator applyPlaceholder(RequestCreator bitmapRequest) {
    if (placeholderId != null) {
      bitmapRequest.placeholder(placeholderId);
    }
    return bitmapRequest;
  }

  /**
   * Lazy instantiation of the list of transformations used during the image download. This method
   * returns a List<Transformation> because Picasso doesn't support a null instance as
   * transformation.
   */
  private List<Transformation> getTransformations() {
    if (transformations == null) {
      transformations = new LinkedList<Transformation>();
      if (useCircularTransformation) {
        transformations.add(new CircleTransformation());
      }
    }
    return transformations;
  }

  private void validateListener(Listener listener) {
    if (listener == null) {
      throw new NullPointerException(
          "You can't pass a null instance of GlideImageLoader.Listener.");
    }
  }
}

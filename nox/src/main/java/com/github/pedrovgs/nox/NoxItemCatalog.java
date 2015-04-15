package com.github.pedrovgs.nox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.pedrovgs.nox.transformation.CircularTransformation;
import java.util.List;
import java.util.Observable;

/**
 * Processes NoxItem instances to download the images associated to a set of NoxItem instances
 * asynchronously. Notifies when a new bitmap is ready to be used to the class clients.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */

//TODO: Add code to release the image loading if needed and remove observables to avoid memory leaks.
class NoxItemCatalog extends Observable {

  private final Context context;
  private final List<NoxItem> noxItems;
  private final int noxItemSize;
  private final Bitmap[] bitmaps;
  private final Drawable[] placeholders;
  private Drawable placeholder;

  NoxItemCatalog(Context context, List<NoxItem> noxItems, int noxItemSize) {
    validateNoxItems(noxItems);
    this.context = context;
    this.noxItems = noxItems;
    this.noxItemSize = noxItemSize;
    this.bitmaps = new Bitmap[noxItems.size()];
    this.placeholders = new Drawable[noxItems.size()];
  }

  int size() {
    return noxItems.size();
  }

  boolean isBitmapReady(int position) {
    return bitmaps[position] != null;
  }

  Bitmap getBitmap(int position) {
    return bitmaps[position];
  }

  boolean isPlaceholderReady(int position) {
    return placeholders[position] != null || placeholder != null;
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

  void loadBitmaps() {
    for (int i = 0; i < noxItems.size(); i++) {
      NoxItem noxItem = noxItems.get(i);
      if (noxItem.hasUrl()) {
        loadBitmapFromUrl(i, noxItem);
      } else if (noxItem.hasResourceId()) {
        loadBitmapFromResource(i, noxItem);
      }
    }
  }

  private void loadBitmapFromUrl(int position, NoxItem noxItem) {
    String url = noxItem.getUrl();
    NoxItemTarget noxItemTarget = getNoxItemTarget(position);
    if (noxItem.hasPlaceholder()) {
      int placeholderId = noxItem.getPlaceholderId();
      Glide.with(context)
          .load(url)
          .asBitmap()
          .placeholder(placeholderId)
          .transform(new CircularTransformation(context))
          .into(noxItemTarget);
    } else {
      Glide.with(context)
          .load(url)
          .asBitmap()
          .transform(new CircularTransformation(context))
          .into(noxItemTarget);
    }
  }

  private void loadBitmapFromResource(int position, NoxItem noxItem) {
    int resourceId = noxItem.getResourceId();
    NoxItemTarget noxItemTarget = getNoxItemTarget(position);
    if (noxItem.hasPlaceholder()) {
      int placeholderId = noxItem.getPlaceholderId();
      Glide.with(context)
          .load(resourceId)
          .asBitmap()
          .placeholder(placeholderId)
          .transform(new CircularTransformation(context))
          .into(noxItemTarget);
    } else {
      Glide.with(context)
          .load(resourceId)
          .asBitmap()
          .transform(new CircularTransformation(context))
          .into(noxItemTarget);
    }
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
  }

  private NoxItemTarget getNoxItemTarget(int position) {
    return new NoxItemTarget(position, noxItemSize);
  }

  private class NoxItemTarget extends SimpleTarget<Bitmap> {

    private final int position;

    public NoxItemTarget(int position, int size) {
      super(size, size);
      this.position = position;
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
      bitmaps[position] = resource;
      notifyNoxItemReady();
    }

    @Override public void onLoadStarted(Drawable placeholder) {
      super.onLoadStarted(placeholder);
      placeholders[position] = placeholder;
      if (placeholder != null) {
        notifyNoxItemReady();
      }
    }
  }

  private void notifyNoxItemReady() {
    setChanged();
    notifyObservers();
  }
}

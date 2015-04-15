package com.github.pedrovgs.nox;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import java.util.List;

/**
 * Processes NoxItem instances to download the images associated to a set of NoxItem instances
 * asynchronously. Notifies when a new bitmap is ready to be used to the class clients.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
class NoxItemCatalog {

  private final List<NoxItem> noxItems;
  private final Bitmap[] bitmaps;
  private Drawable placeholder;

  public NoxItemCatalog(List<NoxItem> noxItems) {
    validateNoxItems(noxItems);
    this.noxItems = noxItems;
    this.bitmaps = new Bitmap[noxItems.size()];
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
  }

  public int size() {
    return noxItems.size();
  }

  public Bitmap getBitmap(int position) {
    return null;
  }

  public void setPlaceholder(Drawable placeholder) {
    this.placeholder = placeholder;
  }

  public void loadBitmaps() {

  }
}

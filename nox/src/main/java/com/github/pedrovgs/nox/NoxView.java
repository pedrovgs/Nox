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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.github.pedrovgs.nox.imageloader.ImageLoader;
import com.github.pedrovgs.nox.imageloader.ImageLoaderFactory;
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import com.github.pedrovgs.nox.path.PathFactory;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Main library component. This custom view is going to receive a List of Nox objects and create a
 * awesome panel full of images. This new UI component is going to be similar to the Apple's watch
 * main menu user interface http://cdni.wired.co.uk/1240x826/a_c/apple-watch-6_1.jpg
 *
 * NoxItem objects are going to be used to render the user interface using the resource used
 * to render inside each element and a view holder.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public class NoxView extends View {

  private NoxConfig noxConfig;
  private Path path;
  private Scroller scroller;
  private NoxItemCatalog noxItemCatalog;
  private Paint paint = new Paint();
  private boolean wasInvalidatedBefore;

  public NoxView(Context context) {
    this(context, null);
  }

  public NoxView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NoxView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initializeNoxViewConfig(context, attrs, defStyleAttr, 0);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public NoxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initializeNoxViewConfig(context, attrs, defStyleAttr, defStyleRes);
  }

  /**
   * Given a List<NoxItem> instances configured previously gets the associated resource to draw the
   * view.
   */
  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //TODO: Check if we can move to onScroll or computeScroll method
    updatePathOffset();
    for (int i = 0; i < noxItemCatalog.size(); i++) {
      if (path.isItemInsideView(i)) {
        loadNoxItem(i);
        float left = path.getXForItemAtPosition(i);
        float top = path.getYForItemAtPosition(i);
        drawNoxItem(canvas, i, left, top);
      }
    }
    wasInvalidatedBefore = false;
  }

  /**
   * Configures a of List<NoxItem> instances to draw this items.
   */
  public void showNoxItems(final List<NoxItem> noxItems) {
    this.post(new Runnable() {
      @Override public void run() {
        initializeNoxItemCatalog(noxItems);
        createPath();
        initializeScroller();
        refreshView();
      }
    });
  }

  /**
   * Changes the Path used to the one passed as argument. This method will refresh the view.
   */
  public void setPath(Path path) {
    validatePath(path);
    this.path = path;
    initializeScroller();
    refreshView();
  }

  /**
   * Delegates touch events to the scroller instance initialized previously to implement the scroll
   * effect.
   */
  @Override public boolean onTouchEvent(MotionEvent event) {
    if (scroller == null) {
      return false;
    }
    return scroller.onTouchEvent(event);
  }

  /**
   * Delegates computeScroll method to the scroller instance to implement the scroll effect.
   */
  @Override public void computeScroll() {
    super.computeScroll();
    if (scroller != null) {
      scroller.computeScroll();
    }
  }

  /**
   * Controls visibility changes to pause or resume this custom view and avoid preformance
   * problems.
   */
  @Override protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (changedView != this) {
      return;
    }

    if (visibility == View.VISIBLE) {
      resume();
    } else {
      pause();
    }
  }

  /**
   * Given a NoxItem position try to load this NoxItem if the view is not performing a fast scroll
   * after a fling gesture.
   */
  private void loadNoxItem(int position) {
    if (!scroller.isScrollingFast()) {
      noxItemCatalog.load(position);
    }
  }

  /**
   * Resumes NoxItemCatalog and adds a observer to be notified when a NoxItem is ready to be drawn.
   */
  private void resume() {
    noxItemCatalog.addObserver(catalogObserver);
    noxItemCatalog.resume();
  }

  /**
   * Pauses NoxItemCatalog and removes the observer previously configured.
   */
  private void pause() {
    noxItemCatalog.pause();
    noxItemCatalog.deleteObserver(catalogObserver);
  }

  /**
   * Observer implementation used to be notified when a NoxItem has been loaded.
   */
  private Observer catalogObserver = new Observer() {
    @Override public void update(Observable observable, Object data) {
      Integer position = (Integer) data;
      boolean isNoxItemLoadedInsideTheView = path != null && path.isItemInsideView(position);
      if (isNoxItemLoadedInsideTheView) {
        refreshView();
      }
    }
  };

  /**
   * Tries to post a invalidate() event if another one was previously posted.
   */
  private void refreshView() {
    if (!wasInvalidatedBefore) {
      wasInvalidatedBefore = true;
      invalidate();
    }
  }

  private void initializeNoxItemCatalog(List<NoxItem> noxItems) {
    ImageLoader imageLoader = ImageLoaderFactory.getPicassoImageLoader(getContext());
    this.noxItemCatalog =
        new NoxItemCatalog(noxItems, (int) noxConfig.getNoxItemSize(), imageLoader);
    this.noxItemCatalog.setPlaceholder(noxConfig.getPlaceholder());
    this.noxItemCatalog.addObserver(catalogObserver);
  }

  private void initializeScroller() {
    scroller = new Scroller(this, path.getMinX(), path.getMaxX(), path.getMinY(), path.getMaxY(),
        path.getOverSize());
  }

  /**
   * Checks the X and Y scroll offset to update that values into the Path configured previously.
   */
  private void updatePathOffset() {
    int offsetX = scroller.getOffsetX();
    int offsetY = scroller.getOffsetY();
    path.setOffset(offsetX, offsetY);
  }

  /**
   * Draws a NoxItem during the onDraw method.
   */
  private void drawNoxItem(Canvas canvas, int position, float left, float top) {
    if (noxItemCatalog.isBitmapReady(position)) {
      Bitmap bitmap = noxItemCatalog.getBitmap(position);
      canvas.drawBitmap(bitmap, left, top, paint);
    } else if (noxItemCatalog.isPlaceholderReady(position)) {
      Drawable drawable = noxItemCatalog.getPlaceholder(position);
      if (drawable != null) {
        int itemSize = (int) noxConfig.getNoxItemSize();
        drawable.setBounds((int) left, (int) top, (int) left + itemSize, (int) top + itemSize);
        drawable.draw(canvas);
      }
    }
  }

  /**
   * Initializes a Path instance given the NoxView configuration provided programmatically or using
   * XML styleable attributes.
   */
  private void createPath() {
    if (path == null) {
      float firstItemMargin = noxConfig.getNoxItemMargin();
      float firstItemSize = noxConfig.getNoxItemSize();
      int viewHeight = getMeasuredHeight();
      int viewWidth = getMeasuredWidth();
      int numberOfElements = noxItemCatalog.size();
      PathConfig pathConfig =
          new PathConfig(numberOfElements, viewWidth, viewHeight, firstItemSize, firstItemMargin);
      path = PathFactory.getFixedCircularPath(pathConfig);
    } else {
      path.setNumberOfElements(noxItemCatalog.size());
    }
    path.calculate();
  }

  /**
   * Initializes a NoxConfig instance given the NoxView configuration provided programmatically or
   * using XML styleable attributes.
   */
  private void initializeNoxViewConfig(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    noxConfig = new NoxConfig();
    TypedArray attributes = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.nox, defStyleAttr, defStyleRes);
    initializeNoxItemSize(attributes);
    initializeNoxItemMargin(attributes);
    initializeNoxItemPlaceholder(attributes);
    attributes.recycle();
  }

  /**
   * Configures the nox item default size used in NoxConfig, Path and NoxItemCatalog to draw nox
   * item instances during the onDraw execution.
   */
  private void initializeNoxItemSize(TypedArray attributes) {
    float noxItemSizeDefaultValue = getResources().getDimension(R.dimen.default_nox_item_size);
    float noxItemSize = attributes.getDimension(R.styleable.nox_item_size, noxItemSizeDefaultValue);
    noxConfig.setNoxItemSize(noxItemSize);
  }

  /**
   * Configures the nox item default margin used in NoxConfig, Path and NoxItemCatalog to draw nox
   * item instances during the onDraw execution.
   */
  private void initializeNoxItemMargin(TypedArray attributes) {
    float noxItemMarginDefaultValue = getResources().getDimension(R.dimen.default_nox_item_margin);
    float noxItemMargin =
        attributes.getDimension(R.styleable.nox_item_margin, noxItemMarginDefaultValue);
    noxConfig.setNoxItemMargin(noxItemMargin);
  }

  /**
   * Configures the placeholder used if there is no another placeholder configured in the NoxItem
   * instances during the onDraw execution.
   */
  private void initializeNoxItemPlaceholder(TypedArray attributes) {
    Drawable placeholder = attributes.getDrawable(R.styleable.nox_placeholder);
    noxConfig.setPlaceholder(placeholder);
  }

  private void validatePath(Path path) {
    if (path == null) {
      throw new NullPointerException("You can't pass a null Path instance as argument.");
    }
    if (noxItemCatalog != null && path.getNumberOfElements() != noxItemCatalog.size()) {
      throw new IllegalArgumentException(
          "The number of items in the Path instance passed as argument doesn't match with the current number of NoxItems.");
    }
  }

  /**
   * Method created for testing purposes. Returns the Scroller used internally to implement the
   * scroll effect.
   */
  Scroller getScroller() {
    return scroller;
  }

  /**
   * Method created for testing purposes. Configures the Scroller to be used by NoxView.
   */
  void setScroller(Scroller scroller) {
    this.scroller = scroller;
  }

  /**
   * Method created for testing purposes. Returns the NoxItemCatalog used internally to implement
   * the
   * scroll effect.
   */
  NoxItemCatalog getNoxItemCatalog() {
    return noxItemCatalog;
  }

  /**
   * Method created for testing purposes. Configures the NoxItemCatalog to be used by NoxView.
   */
  void setNoxItemCatalog(NoxItemCatalog noxItemCatalog) {
    this.noxItemCatalog = noxItemCatalog;
  }
}

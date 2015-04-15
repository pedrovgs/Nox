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
import com.github.pedrovgs.nox.path.Path;
import com.github.pedrovgs.nox.path.PathConfig;
import com.github.pedrovgs.nox.path.PathFactory;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Main library component. This custom view is going to receive a List of Nox objects and create a
 * awesome panel full of images. This new UI component is going to simulate the Apple's watch main
 * menu user interface http://cdni.wired.co.uk/1240x826/a_c/apple-watch-6_1.jpg
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

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    updatePathOffset();
    for (int i = 0; i < noxItemCatalog.size(); i++) {
      if (path.isItemInsideView(i)) {
        float left = path.getLeftForItemAtPosition(i);
        float top = path.getTopForItemAtPosition(i);
        drawNoxItem(canvas, i, left, top);
      }
    }
  }

  /**
   * Given a List<NoxItem> draws this items keeping the previous view state.
   */
  public void showNoxItems(List<NoxItem> noxItems) {
    initializeNoxItemCatalog(noxItems);
    this.post(new Runnable() {
      @Override public void run() {
        createPath();
        initializeScroller();
        invalidate();
      }
    });
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return scroller.getGestureDetector().onTouchEvent(event);
  }

  @Override public void computeScroll() {
    super.computeScroll();
    scroller.computeScroll();
  }

  private void initializeNoxItemCatalog(List<NoxItem> noxItems) {
    this.noxItemCatalog =
        new NoxItemCatalog(getContext(), noxItems, (int) noxConfig.getNoxItemSize());
    this.noxItemCatalog.setPlaceholder(noxConfig.getPlaceholder());
    this.noxItemCatalog.addObserver(new Observer() {
      @Override public void update(Observable observable, Object data) {
        Integer position = (Integer) data;
        boolean isNoxItemLoadedInsideTheView = path != null && path.isItemInsideView(position);
        if (isNoxItemLoadedInsideTheView) {
          invalidate();
        }
      }
    });
    this.noxItemCatalog.loadBitmaps();
  }

  private void initializeScroller() {
    scroller = new Scroller(this, path.getMinX(), path.getMaxX(), path.getMinY(), path.getMaxY(),
        path.getOverSize());
  }

  private void updatePathOffset() {
    int offsetX = scroller.getOffsetX();
    int offsetY = scroller.getOffsetY();
    path.setOffset(offsetX, offsetY);
  }

  private void drawNoxItem(Canvas canvas, int position, float left, float top) {
    if (noxItemCatalog.isBitmapReady(position)) {
      Bitmap bitmap = noxItemCatalog.getBitmap(position);
      canvas.drawBitmap(bitmap, left, top, paint);
    } else if (noxItemCatalog.isPlaceholderReady(position)) {
      Drawable drawable = noxItemCatalog.getPlaceholder(position);
      int itemSize = (int) noxConfig.getNoxItemSize();
      drawable.setBounds((int) left, (int) top, (int) left + itemSize, (int) top + itemSize);
      drawable.draw(canvas);
    }
  }

  private void createPath() {
    float firstItemMargin = noxConfig.getNoxItemMargin();
    float firstItemSize = noxConfig.getNoxItemSize();
    int viewHeight = getMeasuredHeight();
    int viewWidth = getMeasuredWidth();
    int numberOfElements = noxItemCatalog.size();
    PathConfig pathConfig =
        new PathConfig(numberOfElements, viewWidth, viewHeight, firstItemSize, firstItemMargin);
    path = PathFactory.getLinearPath(pathConfig);
    path.calculate();
  }

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

  private void initializeNoxItemSize(TypedArray attributes) {
    float noxItemSizeDefaultValue = getResources().getDimension(R.dimen.default_nox_item_size);
    float noxItemSize = attributes.getDimension(R.styleable.nox_item_size, noxItemSizeDefaultValue);
    noxConfig.setNoxItemSize(noxItemSize);
  }

  private void initializeNoxItemMargin(TypedArray attributes) {
    float noxItemMarginDefaultValue = getResources().getDimension(R.dimen.default_nox_item_margin);
    float noxItemMargin =
        attributes.getDimension(R.styleable.nox_item_margin, noxItemMarginDefaultValue);
    noxConfig.setNoxItemMargin(noxItemMargin);
  }

  private void initializeNoxItemPlaceholder(TypedArray attributes) {
    Drawable placeholder = attributes.getDrawable(R.styleable.nox_placeholder);
    noxConfig.setPlaceholder(placeholder);
  }
}

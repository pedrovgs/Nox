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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

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

  private List<NoxItem> noxItems;
  private NoxConfig noxConfig;
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

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int y = getMeasuredHeight() / 2;
    int x = getMeasuredWidth() / 2;
    Integer resourceId = noxItems.get(0).getResourceId();
    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
    canvas.drawBitmap(bitmap, x, y, paint);
  }

  /**
   * Given a List<NoxItem> draws this items keeping the previous view state.
   */
  public void showNoxItems(List<NoxItem> noxItems) {
    validateNoxItems(noxItems);
    this.noxItems = noxItems;
    invalidate();
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
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
    if (placeholder == null) {
      //TODO: Change this with a white circle or another placeholder.
      placeholder = getContext().getResources().getDrawable(R.drawable.abc_ic_clear_mtrl_alpha);
    }
    noxConfig.setPlaceholder(placeholder);
  }
}

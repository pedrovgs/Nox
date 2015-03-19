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
import android.graphics.Canvas;
import android.graphics.Paint;
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
  
  public NoxView(Context context) {
    super(context);
  }

  public NoxView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoxView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public NoxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
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
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(x, y, 100, paint);
  }

  /**
   * Given a List<NoxItem> draws this items keeping the previous view state.
   */
  public void showNoxItems(List<NoxItem> noxItems) {
    validateNoxItems(noxItems);
    this.noxItems = noxItems;
  }

  private void validateNoxItems(List<NoxItem> noxItems) {
    if (noxItems == null) {
      throw new NullPointerException("The list of NoxItem can't be null");
    }
  }
}

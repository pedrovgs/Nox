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

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.github.pedrovgs.nox.doubles.FakeShape;
import com.github.pedrovgs.nox.shape.Shape;
import com.github.pedrovgs.nox.shape.ShapeConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author Pedro Vicente Gomez Sanchez.
 */
@Config(emulateSdk = 18) @RunWith(RobolectricTestRunner.class) public class NoxViewTest {

  private static final int ANY_RESOURCE_ID = R.drawable.abc_ab_share_pack_holo_dark;
  private static final int ANY_MIN_X = -50;
  private static final int ANY_MAX_X = 50;
  private static final int ANY_MIN_Y = -70;
  private static final int ANY_MAX_Y = 70;
  private static final int ANY_OVER_SIZE = 10;
  private static final int ANY_VIEW_WIDTH = 100;
  private static final int ANY_VIEW_HEIGHT = 100;
  private static final int ANY_ITEM_SIZE = 8;
  private static final int ANY_ITEM_MARGIN = 2;

  private NoxView noxView;

  @Before public void setUp() {
    Activity activity = Robolectric.buildActivity(Activity.class).create().resume().get();
    noxView = new NoxView(activity);
  }

  @Test(expected = NullPointerException.class) public void shouldNotAcceptANullNoxItemListToShow() {
    noxView.showNoxItems(null);
  }

  @Test public void shouldAcceptAnEmptyListOfNoxItems() {
    noxView.showNoxItems(Collections.EMPTY_LIST);
  }

  @Test public void shouldAcceptANonEmptyListOfNoxItems() {
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
  }

  @Test public void shouldInitializeScrollerUsingShapeInformation() {
    Shape shape =
        givenAShapeConfiguredToReturn(ANY_MIN_X, ANY_MAX_X, ANY_MIN_Y, ANY_MAX_Y, ANY_OVER_SIZE);

    noxView.setShape(shape);

    assertEquals(ANY_MIN_X, noxView.getMinX());
    assertEquals(ANY_MAX_X, noxView.getMaxX());
    assertEquals(ANY_MIN_Y, noxView.getMinY());
    assertEquals(ANY_MAX_Y, noxView.getMaxY());
    assertEquals(ANY_OVER_SIZE, noxView.getOverSize());
  }

  @Test public void shouldInvalidateViewOnNewShapeConfigured() {
    noxView = spy(noxView);
    Shape shape =
        givenAShapeConfiguredToReturn(ANY_MIN_X, ANY_MAX_X, ANY_MIN_Y, ANY_MAX_Y, ANY_OVER_SIZE);

    noxView.setShape(shape);

    verify(noxView).invalidate();
  }

  @Test(expected = NullPointerException.class) public void shouldNotAcceptNullShapeInstances() {
    noxView.setShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotAcceptANewShapeWithDifferentNumberOfElementsThanThePreviousConfigured() {
    Shape shape = givenAShapeWithNumberOfElements(2);
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
    noxView.setShape(shape);
  }

  @Test public void shouldInvalidateViewOnNewNoxItemsConfigured() {
    noxView = spy(noxView);

    noxView.showNoxItems(Collections.EMPTY_LIST);

    verify(noxView).invalidate();
  }

  @Test public void shouldChangeShpaeNumberOfElementsIfNoxItemsWherePreviouslyConfigured() {
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();
    Shape shape = givenAShapeWithNumberOfElements(noxItems.size() + 1);
    shape = spy(shape);

    noxView.setShape(shape);
    noxView.showNoxItems(noxItems);

    verify(shape).setNumberOfElements(noxItems.size());
  }

  @Test public void shouldNotHandleOnTouchEventIfNoNoxItemHaveBeenShown() {
    boolean handled = noxView.performClick();

    assertFalse(handled);
  }

  @Test public void shouldDelegateOnTouchEventsToScroller() {
    Scroller scroller = mock(Scroller.class);
    noxView.setScroller(scroller);

    MotionEvent touchEvent = mock(MotionEvent.class);
    noxView.onTouchEvent(touchEvent);

    verify(scroller).onTouchEvent(touchEvent);
  }

  @Test public void shouldDelegateComputeScrollMethodToScroller() {
    Scroller scroller = mock(Scroller.class);
    noxView.setScroller(scroller);

    noxView.computeScroll();

    verify(scroller).computeScroll();
  }

  @Test public void shouldPauseNoxItemCatalogOnVisibilityChangedToNotVisible() {
    NoxItemCatalog noxItemCatalog = mock(NoxItemCatalog.class);
    noxView.setNoxItemCatalog(noxItemCatalog);

    noxView.setVisibility(View.INVISIBLE);

    verify(noxItemCatalog).pause();
  }

  @Test public void shouldResumeNoxItemCatalogOnVisibilityChangedToVisible() {
    NoxItemCatalog noxItemCatalog = mock(NoxItemCatalog.class);
    noxView.setNoxItemCatalog(noxItemCatalog);

    noxView.setVisibility(View.INVISIBLE);
    noxView.setVisibility(View.VISIBLE);

    verify(noxItemCatalog).resume();
  }

  @Test public void shouldNotInteractWithNoxItemCatalogIfChangedViewIsNotNoxView() {
    NoxItemCatalog noxItemCatalog = mock(NoxItemCatalog.class);
    noxView.setNoxItemCatalog(noxItemCatalog);

    noxView.onVisibilityChanged(mock(View.class), View.VISIBLE);

    verify(noxItemCatalog, never()).resume();
    verify(noxItemCatalog, never()).pause();
  }

  @Test public void shouldInvalidateViewOnNewNoxItemReady() {
    noxView = spy(noxView);
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
    NoxItemCatalog noxItemCatalog = noxView.getNoxItemCatalog();
    noxItemCatalog.notifyNoxItemReady(0);

    verify(noxView).invalidate();
  }

  @Test public void shouldNotInvalidateViewIfNoxViewIsNotVisible() {
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
    NoxItemCatalog noxItemCatalog = noxView.getNoxItemCatalog();
    noxView = spy(noxView);
    noxView.setVisibility(View.INVISIBLE);
    noxItemCatalog.notifyNoxItemReady(0);

    verify(noxView, never()).invalidate();
  }

  @Test public void shouldNotDrawAnyNoxItemIfNoxItemCatalogHasNotDownloadAnyResource() {
    Canvas canvas = mock(Canvas.class);
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
    noxView.draw(canvas);

    verify(canvas, never()).drawBitmap(any(Bitmap.class), anyInt(), anyInt(), any(Paint.class));
  }

  @Test public void shouldNotDrawAnyNoxItemIfTheNoxItemCatalogIsEmpty() {
    Canvas canvas = mock(Canvas.class);

    noxView.showNoxItems(Collections.EMPTY_LIST);
    noxView.onDraw(canvas);

    verify(canvas, never()).drawBitmap(any(Bitmap.class), anyInt(), anyInt(), any(Paint.class));
  }

  @Test public void shouldDrawNoxItemResourcesIfAreDownloaded() {
    Canvas canvas = mock(Canvas.class);
    Bitmap bitmap = mock(Bitmap.class);
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();

    noxView.showNoxItems(noxItems);
    NoxItemCatalog noxItemCatalog = noxView.getNoxItemCatalog();
    noxItemCatalog.setBitmap(0, bitmap);
    noxItemCatalog.notifyNoxItemReady(0);
    noxView.onDraw(canvas);

    verify(canvas).drawBitmap(eq(bitmap), anyInt(), anyInt(), any(Paint.class));
  }

  @Test public void shouldNotDrawNoxItemsOutOfTheViewEvenIfAreReadyToDraw() {
    Canvas canvas = mock(Canvas.class);
    List<NoxItem> noxItems = givenOneListWithJustOneNoxItem();
    Shape shape = givenAShapeToPositionElementsOutOfTheView();

    noxView.setShape(shape);
    noxView.showNoxItems(noxItems);
    NoxItemCatalog noxItemCatalog = noxView.getNoxItemCatalog();
    noxItemCatalog.setBitmap(0, mock(Bitmap.class));
    noxItemCatalog.notifyNoxItemReady(0);
    noxView.onDraw(canvas);

    verify(canvas, never()).drawBitmap(any(Bitmap.class), anyInt(), anyInt(), any(Paint.class));
  }

  @Test(expected = NullPointerException.class)
  public void shouldNotAcceptNullInstancesOfOnNoxItemClickListener() {
    noxView.setOnNoxItemClickListener(null);
  }

  private List<NoxItem> givenOneListWithJustOneNoxItem() {
    List<NoxItem> noxItems = new ArrayList<NoxItem>();
    noxItems.add(new NoxItem(ANY_RESOURCE_ID));
    return noxItems;
  }

  private Shape givenAShapeWithNumberOfElements(int numberOfElements) {
    ShapeConfig shapeConfig =
        new ShapeConfig(numberOfElements, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE,
            ANY_ITEM_MARGIN);
    return new FakeShape(shapeConfig);
  }

  private Shape givenAShapeToPositionElementsOutOfTheView() {
    ShapeConfig shapeConfig =
        new ShapeConfig(1, ANY_VIEW_WIDTH, ANY_VIEW_HEIGHT, ANY_ITEM_SIZE, ANY_ITEM_MARGIN);
    FakeShape shape = new FakeShape(shapeConfig);
    shape.setXPosition(-100);
    shape.setYPosition(-200);
    return shape;
  }

  private Shape givenAShapeConfiguredToReturn(int minX, int maxX, int minY, int maxY,
      int overSize) {
    ShapeConfig shapeConfig = new ShapeConfig(1, 0, 0, 0, 0);
    FakeShape path = new FakeShape(shapeConfig);
    path.setBoundaries(minX, maxX, minY, maxY, overSize);
    return path;
  }
}

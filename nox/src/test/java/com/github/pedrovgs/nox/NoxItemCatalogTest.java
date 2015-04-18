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

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.github.pedrovgs.nox.doubles.FakeImageLoader;
import com.github.pedrovgs.nox.imageloader.ImageLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
@Config(emulateSdk = 18) @RunWith(RobolectricTestRunner.class) public class NoxItemCatalogTest {

  private static final int ANY_NOX_ITEM_SIZE = 100;
  private static final String ANY_URL = "http://anyimage.com/1";
  private static final String ANY_URL_2 = "http://anyimage.com/2";
  private static final NoxItem ANY_NOX_ITEM = new NoxItem(ANY_URL);
  private static final Drawable ANY_PLACEHOLDER = new ColorDrawable();
  private static final int ANY_PLACEHOLDER_ID = 2;
  private static final int ANY_RESOURCE_ID = 1;

  @Spy private ImageLoader imageLoader = new FakeImageLoader();
  @Mock Observer observer;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void shouldBaseNoxCatalogSizeInTheListPassedInConstruction() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(ANY_NOX_ITEM);

    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    assertEquals(noxItems.size(), noxItemCatalog.size());
  }

  @Test public void shouldReturnFalseCheckingIfBitmapsAreReadyIfNoxCatalogWasNotLoaded() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    assertFalse(noxItemCatalog.isBitmapReady(0));
  }

  @Test
  public void shouldReturnFalseIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasNotConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    assertFalse(noxItemCatalog.isPlaceholderReady(0));
  }

  @Test public void shouldReturnTrueIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    assertTrue(noxItemCatalog.isPlaceholderReady(0));
  }

  @Test public void shouldReturnNullIfNoxCatalogWasNotLoadedAndOnePlaceholderWasNotConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    assertNull(noxItemCatalog.getPlaceholder(0));
  }

  @Test
  public void shouldReturnPlaceholderIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    assertEquals(ANY_PLACEHOLDER, noxItemCatalog.getPlaceholder(0));
  }

  @Test public void shouldPauseImageLoadingWhenNoxItemCatalogIsPaused() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.pause();

    verify(imageLoader).pause();
  }

  @Test public void shouldResumeImageLoadingWhenNoxItemCatalogIsResumed() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.resume();

    verify(imageLoader).resume();
  }

  @Test public void shouldUseNoxItemCatalogSizeToLoadNoxItemImages() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.load(0);

    verify(imageLoader).size(ANY_NOX_ITEM_SIZE);
  }

  @Test public void shouldUseCircularTransformationToLoadNoxItemImages() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.load(0);

    verify(imageLoader).useCircularTransformation();
  }

  @Test
  public void shouldNotifyObserversWhenAnImageAndPlaceholderAreLoadedIndicatingNoxItemPosition() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();
    noxItemCatalog.addObserver(observer);

    noxItemCatalog.load(0);

    verify(observer, times(2)).update(noxItemCatalog, 0);
  }

  @Test
  public void shouldNotifyObserversWhenNoxItemsAreLoadedIndicatingNoxItemPositionWithSomeElements() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL));
    noxItems.add(new NoxItem(ANY_URL_2));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);
    noxItemCatalog.addObserver(observer);

    noxItemCatalog.load(0);
    noxItemCatalog.load(1);

    verify(observer, times(2)).update(noxItemCatalog, 0);
    verify(observer, times(2)).update(noxItemCatalog, 1);
  }

  @Test public void shouldLoadImagesForEveryNoxItemUsingNoxItemUrl() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL));
    noxItems.add(new NoxItem(ANY_URL_2));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    noxItemCatalog.load(0);
    noxItemCatalog.load(1);

    verify(imageLoader).load(ANY_URL);
    verify(imageLoader).load(ANY_URL_2);
  }

  @Test public void shouldReturnANotNullBitmapWhenNoxItemsAreLoadedUsingUrl() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    noxItemCatalog.load(0);

    assertTrue(noxItemCatalog.isBitmapReady(0));
    assertNotNull(noxItemCatalog.getBitmap(0));
  }

  @Test public void shouldReturnNullPlaceholdersWhenNoxItemsAreLoadedUsingUrl() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    noxItemCatalog.load(0);

    assertFalse(noxItemCatalog.isPlaceholderReady(0));
    assertNull(noxItemCatalog.getPlaceholder(0));
  }

  @Test public void shouldReturnPlaceholdersWhenNoxItemsAreLoadedUsingUrlWithGeneralPlaceholder() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);
    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    noxItemCatalog.load(0);

    assertTrue(noxItemCatalog.isPlaceholderReady(0));
    assertEquals(ANY_PLACEHOLDER, noxItemCatalog.getPlaceholder(0));
  }

  @Test public void shouldReturnANotNullBitmapWhenNoxItemsAreLoadedUsingResourceId() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_RESOURCE_ID));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    noxItemCatalog.load(0);

    assertTrue(noxItemCatalog.isBitmapReady(0));
    assertNotNull(noxItemCatalog.getBitmap(0));
  }

  @Test public void shouldReturnANotNullPlaceholderWhenNoxItemsAreLoaded() {
    List<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(new NoxItem(ANY_URL, ANY_PLACEHOLDER_ID));
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(noxItems);

    noxItemCatalog.load(0);

    assertTrue(noxItemCatalog.isPlaceholderReady(0));
    assertNotNull(noxItemCatalog.getPlaceholder(0));
  }

  @Test public void shouldReturnNullBitmapsOrPlaceholdersUntilImagesAreLoaded() {
    FakeImageLoader imageLoader = new FakeImageLoader(true);
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(imageLoader);

    noxItemCatalog.load(0);

    assertFalse(noxItemCatalog.isPlaceholderReady(0));
    assertNull(noxItemCatalog.getPlaceholder(0));
    assertFalse(noxItemCatalog.isBitmapReady(0));
    assertNull(noxItemCatalog.getBitmap(0));
  }

  @Test public void shouldReturnBitmapsAndPlaceholdersWhenImagesAreLoaded() {
    FakeImageLoader imageLoader = new FakeImageLoader(true);
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(imageLoader);
    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    noxItemCatalog.load(0);
    imageLoader.forceLoad();

    assertTrue(noxItemCatalog.isPlaceholderReady(0));
    assertEquals(ANY_PLACEHOLDER, noxItemCatalog.getPlaceholder(0));
    assertTrue(noxItemCatalog.isBitmapReady(0));
    assertNotNull(noxItemCatalog.getBitmap(0));
  }

  @Test public void shouldNotLoadNoxItemIfBitmapIsReady() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.load(0);
    noxItemCatalog.load(0);

    verify(imageLoader).load(ANY_URL);
  }

  @Test public void shouldNotLoadNoxItemIfBitmapIsBeingLoaded() {
    ImageLoader imageLoader = new FakeImageLoader(true);
    imageLoader = spy(imageLoader);
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(imageLoader);

    noxItemCatalog.load(0);
    noxItemCatalog.load(0);

    verify(imageLoader).load(ANY_URL);
  }

  @Test public void shouldRetryLoadIfLoadFailedBefore() {
    FakeImageLoader imageLoader = new FakeImageLoader(true);
    imageLoader = spy(imageLoader);
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog(imageLoader);

    noxItemCatalog.load(0);
    imageLoader.forceError();
    noxItemCatalog.load(0);

    verify(imageLoader, times(2)).load(ANY_URL);
  }

  private NoxItemCatalog givenOneNoxItemCatalog() {
    LinkedList<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(ANY_NOX_ITEM);
    return givenOneNoxItemCatalog(noxItems);
  }

  private NoxItemCatalog givenOneNoxItemCatalog(ImageLoader imageLoader) {
    LinkedList<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(ANY_NOX_ITEM);
    return givenOneNoxItemCatalog(noxItems, imageLoader);
  }

  private NoxItemCatalog givenOneNoxItemCatalog(List<NoxItem> noxItems) {
    return new NoxItemCatalog(noxItems, ANY_NOX_ITEM_SIZE, imageLoader);
  }

  private NoxItemCatalog givenOneNoxItemCatalog(List<NoxItem> noxItems, ImageLoader imageLoader) {
    return new NoxItemCatalog(noxItems, ANY_NOX_ITEM_SIZE, imageLoader);
  }
}

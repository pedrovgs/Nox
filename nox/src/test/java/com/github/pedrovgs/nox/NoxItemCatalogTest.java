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
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
@Config(emulateSdk = 18) @RunWith(RobolectricTestRunner.class) public class NoxItemCatalogTest {

  private static final int ANY_NOX_ITEM_SIZE = 100;
  private static final NoxItem ANY_NOX_ITEM = new NoxItem("http://anyimage.com/1");
  private static final Drawable ANY_PLACEHOLDER = new ColorDrawable();

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
  public void shouldReturnFalseCheckingIfPlaceholdersAreReadyIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasNotConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    assertFalse(noxItemCatalog.isPlaceholderReady(0));
  }

  @Test
  public void shouldReturnTrueCheckingIfPlaceholdersAreReadyIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    assertTrue(noxItemCatalog.isPlaceholderReady(0));
  }

  @Test
  public void shouldReturnNullGettingOnePlaceholdersIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasNotConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    assertNull(noxItemCatalog.getPlaceholder(0));
  }

  @Test
  public void shouldReturnTheGeneralPlaceholderGettingOnePlaceholdersIfNoxCatalogWasNotLoadedAndGeneralPlaceholderWasConfigured() {
    NoxItemCatalog noxItemCatalog = givenOneNoxItemCatalog();

    noxItemCatalog.setPlaceholder(ANY_PLACEHOLDER);

    assertEquals(ANY_PLACEHOLDER, noxItemCatalog.getPlaceholder(0));
  }

  private NoxItemCatalog givenOneNoxItemCatalog() {
    LinkedList<NoxItem> noxItems = new LinkedList<NoxItem>();
    noxItems.add(ANY_NOX_ITEM);
    return givenOneNoxItemCatalog(noxItems);
  }

  private NoxItemCatalog givenOneNoxItemCatalog(List<NoxItem> noxItems) {
    return new NoxItemCatalog(RuntimeEnvironment.application, noxItems, ANY_NOX_ITEM_SIZE);
  }
}

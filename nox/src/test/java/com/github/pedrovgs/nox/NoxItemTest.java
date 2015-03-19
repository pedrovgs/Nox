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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
public class NoxItemTest {

  private static final String ANY_URL = "http://www.anyurl.com";
  private static final int ANY_RESOURCE_ID = 1;
  private static final int ANY_PLACEHOLDER = 2;

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfTryToCreateANoxItemWithANullUrl() {
    new NoxItem(null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfTryToCreateANoxItemWithAnNullUrlAndAnyPlaceholder() {
    new NoxItem(null, ANY_PLACEHOLDER);
  }

  @Test public void shouldReturnTrueIfHasConfiguredAnUrl() {
    NoxItem noxItem = new NoxItem(ANY_URL);

    assertTrue(noxItem.hasUrl());
  }

  @Test public void shouldReturnFalseIfHasNoConfiguredAnUrl() {
    NoxItem noxItem = new NoxItem(ANY_RESOURCE_ID);

    assertFalse(noxItem.hasUrl());
  }

  @Test public void shouldReturnTrueIfHasConfiguredAResourceId() {
    NoxItem noxItem = new NoxItem(ANY_RESOURCE_ID);

    assertTrue(noxItem.hasResourceId());
  }

  @Test public void shouldReturnFalseIfHasNoConfiguredAResourceId() {
    NoxItem noxItem = new NoxItem(ANY_URL);

    assertFalse(noxItem.hasResourceId());
  }

  @Test public void shouldReturnTrueIfHasPlaceholder() {
    NoxItem noxItem = new NoxItem(ANY_URL, ANY_PLACEHOLDER);

    assertTrue(noxItem.hasPlaceholder());
  }

  @Test public void shouldReturnFalseIfHasNoConfiguredAPlaceholder() {
    NoxItem noxItem = new NoxItem(ANY_URL);

    assertFalse(noxItem.hasPlaceholder());
  }
}

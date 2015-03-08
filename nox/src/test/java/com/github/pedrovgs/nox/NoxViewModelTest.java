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
public class NoxViewModelTest {

  private static final String ANY_URL = "http://www.anyurl.com";
  private static final int ANY_RESOURCE_ID = 1;

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionIfTryToCreateANoxViewModelWithANullUrl() {
    new NoxViewModel(null);
  }

  @Test public void shouldReturnTrueIfHasConfiguredAnUrl() {
    NoxViewModel noxViewModel = new NoxViewModel(ANY_URL);

    assertTrue(noxViewModel.hasUrl());
  }

  @Test public void shouldReturnFalseIfHasNoConfiguredAnUrl() {
    NoxViewModel noxViewModel = new NoxViewModel(ANY_RESOURCE_ID);

    assertFalse(noxViewModel.hasUrl());
  }

  @Test public void shouldReturnTrueIfHasConfiguredAResourceId() {
    NoxViewModel noxViewModel = new NoxViewModel(ANY_RESOURCE_ID);

    assertTrue(noxViewModel.hasResourceId());
  }

  @Test public void shouldReturnFalseIfHasNoConfiguredAResoureceId() {
    NoxViewModel noxViewModel = new NoxViewModel(ANY_URL);

    assertFalse(noxViewModel.hasResourceId());
  }
}

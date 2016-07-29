/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.espresso.matcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link HasBackgroundMatcher}.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class HasBackgroundMatcherTest {

  private Context context;

  @Before
  public void setUp() throws Exception {
    context = InstrumentationRegistry.getContext();
  }

  @Test
  public void verifyViewHasBackground() {
    View viewWithBackground = new View(context);
    int drawable1 = android.support.test.testapp.test.R.drawable.drawable_1;
    int drawable2 = android.support.test.testapp.test.R.drawable.drawable_2;

    viewWithBackground.setBackground(context.getDrawable(drawable1));

    assertTrue(new HasBackgroundMatcher(drawable1).matches(viewWithBackground));
    assertFalse(new HasBackgroundMatcher(drawable2).matches(viewWithBackground));
  }

  @Test
  public void verifyBackgroundWhenBackgroundIsNotSet() {
    View view = new View(context);
    view.setBackground(null);
    int drawable1 = android.support.test.testapp.test.R.drawable.drawable_1;

    assertFalse(new HasBackgroundMatcher(drawable1).matches(view));
  }
}

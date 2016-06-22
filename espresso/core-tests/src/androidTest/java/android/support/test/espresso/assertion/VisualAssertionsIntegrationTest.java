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

package android.support.test.espresso.assertion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.testapp.R;
import android.support.test.testapp.SimpleActivity;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matchesVisually;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests for verifying ViewAssertion for visual matching of a view
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class VisualAssertionsIntegrationTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Rule
  public ActivityTestRule<SimpleActivity> rule
      = new ActivityTestRule<SimpleActivity>(SimpleActivity.class);

  @Test
  public void shouldMatchViewVisuallyWithCorrectBitmapOfItsRepresentation() throws Exception {
    int viewId = R.id.text_simple;
    SimpleActivity activity = rule.getActivity();
    View view = activity.findViewById(viewId);
    Bitmap expectedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
        Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(expectedBitmap);
    view.draw(canvas);
    onView(withId(viewId)).check(matchesVisually(expectedBitmap));
  }

  @Test
  public void shouldNotMatchViewVisuallyWithIncorrectBitmap() throws Exception {
    int viewId = R.id.text_simple;
    SimpleActivity activity = rule.getActivity();
    View view = activity.findViewById(viewId);
    Bitmap expectedBitmap = BitmapFactory.decodeResource(
        InstrumentationRegistry.getTargetContext().getResources(), android.R.drawable.alert_dark_frame);
    expectedException.expect(AssertionFailedError.class);
    onView(withId(viewId)).check(matchesVisually(expectedBitmap));
  }
}

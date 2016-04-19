/*
 * Copyright (C) 2014 The Android Open Source Project
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

package android.support.test.espresso.action;

import android.support.test.testapp.R;
import android.support.test.testapp.SwipeActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.repeatedlyUntil;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.rules.ExpectedException.none;

/**
 * Integration tests for repeat action until view state.
 */
@LargeTest
public class RepeatActionUntilViewStateIntegrationTest extends ActivityInstrumentationTestCase2<SwipeActivity> {

  @Rule
  public ExpectedException expectedException = none();

  @SuppressWarnings("deprecation")
  public RepeatActionUntilViewStateIntegrationTest() {
    // Keep froyo happy.
    super("android.support.test.testapp", SwipeActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getActivity();
  }

  /** Tests that trying to perform action on view repeatedly takes it to the view state matched by given view matcher*/
  public void testPerformingActionOnViewUntilItHasDesiredState() {
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #0"))))
        .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Position #2"))))
        .check(matches(hasDescendant(withText("Position #2"))));
  }

  /** Tests that trying to perform action with view already in desired view state has no effect */
  public void testPerformingActionOnAlreadyAchievedViewStateHasNoEffect() {
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #0"))))
        .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Position #0"))))
        .check(matches(hasDescendant(withText("Position #0"))));
  }

  /** Tests that trying to perform action on view repeatedly with unreachable view state fails after given no of attempts*/
  public void testPerformingActionOnViewWithUnreachableViewStateFailsAfterGivenNoOfAttempts() {
    final int noOfAttempts = 2;
    try {
      onView(withId(R.id.vertical_pager))
          .check(matches(hasDescendant(withText("Position #0"))))
          .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Position #200")), noOfAttempts));
      fail("Expected assertion failure");
    } catch (AssertionFailedError ignored) {
      Log.d(RepeatActionUntilViewStateIntegrationTest.class.getName(), "Expected assertion error");
    }
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #2"))));
  }
}

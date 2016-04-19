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

package android.support.test.espresso.action;

import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.testapp.R;
import android.support.test.testapp.SwipeActivity;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

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
@RunWith(AndroidJUnit4.class)
public class RepeatActionUntilViewStateIntegrationTest {

  @Rule
  public ExpectedException expectedException = none();

  @Rule
  public ActivityTestRule<SwipeActivity> activityTestRule =
      new ActivityTestRule<>(SwipeActivity.class);

  @Test
  public void performingActionRepeatedlyOnViewBringsItToDesiredState() {
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #0"))))
        .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Position #2")), 10))
        .check(matches(hasDescendant(withText("Position #2"))));
  }

  @Test
  public void performingActionOnAlreadyAchievedViewStateHasNoEffect() {
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #0"))))
        .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Position #0")), 10))
        .check(matches(hasDescendant(withText("Position #0"))));
  }

  @Test
  public void performingActionOnViewWithUnreachableViewStateFailsAfterGivenNoOfRepeats() {
    final int maxRepeats = 2;
    expectedException.expect(new CustomTypeSafeMatcher<PerformException>("PerformException " +
        "with expected cause and action description") {
      @Override
      protected boolean matchesSafely(PerformException performException) {

        return performException.getCause().getMessage().equals("Failed to achieve view state " +
            "after " + maxRepeats + " repeats") && performException.getActionDescription()
            .equals("fast swipe until: has descendant: with text: is \"Position #200\"");
      }
    });
    onView(withId(R.id.vertical_pager))
        .check(matches(hasDescendant(withText("Position #0"))))
        .perform(repeatedlyUntil(swipeUp(),
            hasDescendant(withText("Position #200")), maxRepeats));
  }
}

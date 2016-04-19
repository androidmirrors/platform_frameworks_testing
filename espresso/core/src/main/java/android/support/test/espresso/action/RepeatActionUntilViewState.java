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
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.view.View;
import android.widget.ViewFlipper;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static android.support.test.internal.util.Checks.checkNotNull;
import static android.support.test.internal.util.Checks.checkState;

/**
 * Enables performing a given action on a view until it reaches desired state matched by given
 * View matcher. This action is useful in the scenarios where performing the action repeatedly
 * on a view changes its state at runtime. For example, if there is a {@link ViewFlipper} on which
 * user can swipe through the views and it automatically flips between each child at a regular
 * interval, it is not always certain which of the child is displayed at a given point of time.
 * In this case, in order to perform click on child no. 4 (assuming child no. 4 contains a text
 * "Child 4"), repeat action can be used as follows - <br/><br/>
 * <p>int maxRepeats=10;<br/>
 *   onView(withId(R.id.my_pager))
 *            .perform(repeatedlyUntil(swipeUp(), hasDescendant(withText("Child 4")), maxRepeats),
 *            click());
 * </p>
 */
public final class RepeatActionUntilViewState implements ViewAction {

  private final ViewAction mAction;
  private final Matcher<View> mDesiredStateMatcher;
  private final int mMaxRepeats;

  protected RepeatActionUntilViewState(ViewAction action, Matcher<View> desiredStateMatcher,
                                       int maxRepeats) {
    checkNotNull(action);
    checkNotNull(desiredStateMatcher);
    checkState(maxRepeats > 1, "maxRepeats should be greater than 1");
    this.mAction = action;
    this.mDesiredStateMatcher = desiredStateMatcher;
    this.mMaxRepeats = maxRepeats;
  }

  @Override
  public Matcher<View> getConstraints() {
    return mAction.getConstraints();
  }

  @Override
  public String getDescription() {
    StringDescription stringDescription = new StringDescription();
    mDesiredStateMatcher.describeTo(stringDescription);
    return String.format("%s until: %s", mAction.getDescription(), stringDescription);
  }

  @Override
  public void perform(UiController uiController, View view) {
    int noOfRepeats = 1;
    for (; !mDesiredStateMatcher.matches(view) && noOfRepeats <= mMaxRepeats; noOfRepeats++) {
      mAction.perform(uiController, view);
      uiController.loopMainThreadUntilIdle();
    }
    if (noOfRepeats > mMaxRepeats) {
      throw new PerformException.Builder()
          .withActionDescription(this.getDescription())
          .withViewDescription(HumanReadables.describe(view))
          .withCause(new RuntimeException(
              String.format("Failed to achieve view state after %d repeats", mMaxRepeats)))
          .build();
    }
  }

}

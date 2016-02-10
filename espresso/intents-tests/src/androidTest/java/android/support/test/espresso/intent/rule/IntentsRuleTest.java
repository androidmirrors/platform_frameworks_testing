/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.espresso.intent.rule;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.intent.IntentStubberRegistry;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@IntentsTestRule}.
 */
@RunWith(AndroidJUnit4.class)
public class IntentsRuleTest {

  Description mDescriptionStub = Description
          .createTestDescription(IntentsRuleTest.class, "someTest");

  @Test
  public void applyRule_InitsAndReleasesIntents() {
    IntentsTestRule<StubActivity> intentsTestRule = new IntentsTestRule<>(StubActivity.class);
    assertThat(IntentStubberRegistry.isLoaded(), is(false));

    // Check that the Intent Stubber is registered when base is evaluated
    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        assertThat(IntentStubberRegistry.isLoaded(), is(true));
      }
    };

    // Apply the TestRule
    intentsTestRule.apply(base, mDescriptionStub);

    // Check that Intents was released.
    assertThat(IntentStubberRegistry.isLoaded(), is(false));
  }

  @Test
  public void withNoLaunchConstructor_WillNotBeInitializedAfterCreation() {
    new IntentsTestRule<>(StubActivity.class, false, false);

    assertThat(IntentStubberRegistry.isLoaded(), is(false));
  }

  @Test
  public void withNoLaunchConstructor_WillNotInitWithoutExplicitCallToLaunchActivity() {
    IntentsTestRule<StubActivity> intentsTestRule = new IntentsTestRule<>(StubActivity.class, false, false);

    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        assertThat(IntentStubberRegistry.isLoaded(), is(false));
      }
    };

    intentsTestRule.apply(base, mDescriptionStub);
  }

  @Test
  public void withNoLaunchConstructor_WillInitWithExplicitCallToLaunchActivity() {
    final IntentsTestRule<StubActivity> intentsTestRule =
            new IntentsTestRule<>(StubActivity.class, false, false);

    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        launchStubActivityWithRule(intentsTestRule);
        assertThat(IntentStubberRegistry.isLoaded(), is(true));
      }
    };

    intentsTestRule.apply(base, mDescriptionStub);
  }

  @Test
  public void withNoLaunchConstructor_WillReleaseWithExplicitCallToLaunchActivity() {
    final IntentsTestRule<StubActivity> intentsTestRule =
            new IntentsTestRule<>(StubActivity.class, false, false);

    Statement base = new Statement() {
      @Override
      public void evaluate() throws Throwable {
        launchStubActivityWithRule(intentsTestRule);
      }
    };

    intentsTestRule.apply(base, mDescriptionStub);

    assertThat(IntentStubberRegistry.isLoaded(), is(false));
  }

  @Test
  public void withFailedToLaunchActivity_WillNotCallToReleaseIntents() {
    // If an Activity started with an IntentsTestRule fails to launch, Intents should not be released because it was never initialized.
    final IntentsTestRule<StubActivity> intentsTestRule = new IntentsTestRule<>(StubActivity.class, false, false);
    // By not launching an Activity with IntentsTestRule, and instead calling afterActivity() directly, we mimic a failure-to-launch Activity.
    intentsTestRule.afterActivityFinished(); // should not throw NPE by trying to release Intents
  }

  private void launchStubActivityWithRule(IntentsTestRule<StubActivity> intentsTestRule) {
    Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), StubActivity.class);
    intentsTestRule.launchActivity(intent);
  }

  public static class StubActivity extends Activity {

  }

}

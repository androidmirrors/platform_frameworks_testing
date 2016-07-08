package android.support.test.testapp;

import android.app.Activity;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(AndroidJUnit4.class)
public class ActivityResultTest {
    @Rule
    public ActivityTestRule<PickActivity> rule = new ActivityTestRule<PickActivity>(PickActivity.class);

    @Test
    public void getResultOkFromPickActivity() {
        onView(withText(R.string.button_pick_number)).perform(click());
        assertThat(rule.getActivityResult(), hasResultCode(Activity.RESULT_OK));
        assertThat(rule.getActivityResult(), hasResultData(IntentMatchers.hasExtraWithKey(PickActivity.EXTRA_PICKED_NUMBER)));
    }

    @Test
    public void getResultCancelFromPickActivity() {
        onView(withText(android.R.string.cancel)).perform(click());
        assertThat(rule.getActivityResult(), hasResultCode(Activity.RESULT_CANCELED));
        assertThat(rule.getActivityResult().getResultData(), is(nullValue()));
    }
}

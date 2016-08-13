package android.support.test.espresso.action;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.testapp.R;
import android.support.test.testapp.ScaledViewActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ClickOnScaledViewIntegrationTest {

    @Rule
    public final ActivityTestRule<ScaledViewActivity> activityRule = new ActivityTestRule<>(ScaledViewActivity.class);

    @Test
    public void clickUnscaledView() {
        onView(withId(R.id.scaled_view)).perform(click());
    }

    @Test
    public void clickToScaleAndClickAgain() {
        onView(withId(R.id.scaled_view)).perform(click());
        onView(withId(R.id.scaled_view)).perform(click());
    }
}

package android.support.test.espresso.contrib;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ActivityResultMatcherTest {

    public static Matcher<Intent> isSameIntent(final Intent expectedIntent) {

        return new TypeSafeMatcher<Intent>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is Intent: " + expectedIntent);
            }

            @Override
            public boolean matchesSafely(Intent intent) {
                return expectedIntent.filterEquals(intent);
            }
        };
    }

    @Test
    public void successHasResultData() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(1, intent);
        assertThat(ActivityResultMatchers.hasResultData(isSameIntent(intent)).matches(activityResult), is(true));
    }

    @Test
    public void failureHasResultData() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(1, intent);
        assertThat(ActivityResultMatchers.hasResultData(isSameIntent(new Intent(Intent.ACTION_VIEW))).matches(activityResult), is(false));
    }

    @Test
    public void successHasResultCode() {
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(1, null);
        assertThat(ActivityResultMatchers.hasResultCode(1).matches(activityResult), is(true));
    }

    @Test
    public void failureHasResultCode() {
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(1, null);
        assertThat(ActivityResultMatchers.hasResultCode(2).matches(activityResult), is(false));
    }
}

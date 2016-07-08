package android.support.test.espresso.contrib;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Hamcrest matchers for a {@link android.app.Instrumentation.ActivityResult}.
 */
public final class ActivityResultMatchers {

    private ActivityResultMatchers() {
        // forbid instantiation
    }

    /**
     * Returns a matcher that verifies that the resultData of a given ActivityResult
     * matches the given specification by the intentMatcher
     */
    public static Matcher<? super Instrumentation.ActivityResult> hasResultData(final Matcher<Intent> intentMatcher) {
        return new TypeSafeMatcher<Instrumentation.ActivityResult>(Instrumentation.ActivityResult.class) {

            @Override
            public void describeTo(Description description) {
                description.appendDescriptionOf(intentMatcher);
            }

            @Override
            protected boolean matchesSafely(Instrumentation.ActivityResult item) {
                return intentMatcher.matches(item.getResultData());
            }

            @Override
            protected void describeMismatchSafely(Instrumentation.ActivityResult item, Description mismatchDescription) {
                intentMatcher.describeMismatch(item.getResultData(), mismatchDescription);
            }
        };
    }

    /**
     * Returns a matcher that verifies that the resultCode of a given ActivityResult
     * matches the given code
     */
    public static Matcher<? super Instrumentation.ActivityResult> hasResultCode(final int resultCode) {
        return new TypeSafeMatcher<Instrumentation.ActivityResult>(Instrumentation.ActivityResult.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has result code " + resultCode);
            }

            @Override
            protected boolean matchesSafely(Instrumentation.ActivityResult activityResult) {
                return activityResult.getResultCode() == resultCode;
            }
        };
    }
}

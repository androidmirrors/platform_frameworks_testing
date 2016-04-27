package android.support.test.runner;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.internal.util.ActivityProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class MonitoringInstrumentationTest {

    private MonitoringInstrumentation instrumentation;

    @Before
    public void setUp() throws Exception {
        instrumentation = (MonitoringInstrumentation) getInstrumentation();
    }

    @Test
    public void shouldCreateNewActivityInstanceUsingDefaultMechanismIfActivityProviderNotSet()
            throws Exception {
        final Class<TestActivity> testActivityClass = TestActivity.class;
        final AtomicReference<Activity> activity = new AtomicReference<>();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.set(instrumentation.newActivity(testActivityClass.getClassLoader(),
                            testActivityClass.getName(), new Intent()));
                } catch (Exception ignored) {

                }
            }
        });

        instrumentation.waitForIdleSync();
        assertThat(activity.get(), instanceOf(TestActivity.class));
    }

    @Test
    public void shouldCreateNewActivityUsingActivityProviderIfSet() throws Exception {
        final Class<TestActivity> testActivityClass = TestActivity.class;
        final AtomicReference<Activity> activity = new AtomicReference<>();

        final TestActivity myTestActivity = mock(TestActivity.class);
        instrumentation.useActivityProvider(activityProvider(myTestActivity, testActivityClass));
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.set(instrumentation.newActivity(testActivityClass.getClassLoader(),
                            testActivityClass.getName(), new Intent()));
                } catch (Exception ignored) {

                }
            }
        });

        instrumentation.waitForIdleSync();
        assertThat(activity.get(), sameInstance((Activity)myTestActivity));
    }

    @Test
    public void shouldCreateNewActivityUsingDefaultMechanismIfActivityProviderSetForOtherActivity()
            throws Exception {
        final Class<TestActivity> testActivityClass = TestActivity.class;
        final AtomicReference<Activity> activity = new AtomicReference<>();

        instrumentation.useActivityProvider(activityProvider(mock(SomeOtherActivity.class), SomeOtherActivity.class));
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.set(instrumentation.newActivity(testActivityClass.getClassLoader(),
                            testActivityClass.getName(), new Intent()));
                } catch (Exception ignored) {

                }
            }
        });

        instrumentation.waitForIdleSync();
        assertThat(activity.get(), instanceOf(TestActivity.class));
    }

    @Test
    public void shouldNotCreateNewActivityUsingActivityProviderIfReset() throws Exception {
        final Class<TestActivity> testActivityClass = TestActivity.class;
        final AtomicReference<Activity> activity = new AtomicReference<>();

        final TestActivity myTestActivity = mock(TestActivity.class);
        instrumentation.useActivityProvider(activityProvider(myTestActivity, testActivityClass));
        instrumentation.resetActivityProvider();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.set(instrumentation.newActivity(testActivityClass.getClassLoader(),
                            testActivityClass.getName(), new Intent()));
                } catch (Exception ignored) {

                }
            }
        });

        instrumentation.waitForIdleSync();
        assertThat(activity.get(), not(sameInstance((Activity)myTestActivity)));
    }

    @NonNull
    private <T extends Activity> ActivityProvider<T> activityProvider(final T activity, final Class<T> activityClass) {
        return new ActivityProvider<T>() {
            @Override
            public T getActivity() {
                return activity;
            }

            @Override
            public Class<T> getActivityClass() {
                return activityClass;
            }
        };
    }

    public static class TestActivity extends Activity {

    }

    public static class SomeOtherActivity extends Activity {

    }
}
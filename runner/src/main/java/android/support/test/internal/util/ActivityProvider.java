package android.support.test.internal.util;

import android.app.Activity;

/*
 * An interface that can be implemented to provide instance of a particular Activity class
 */
public interface ActivityProvider<T extends Activity> {
  T getActivity();
  Class<T> getActivityClass();
}

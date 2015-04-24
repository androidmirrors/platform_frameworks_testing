/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lice`nse is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.rule;

import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class DisableOnDebugTest {
      /**
     * Nasty rule that always fails
     */
    private static class FailOnExecution implements TestRule {
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    private static class ToggleableDisableOnDebug extends DisableOnDebug {
        private final boolean isDebugging;

        ToggleableDisableOnDebug(TestRule delegate, boolean isDebugging) {
            super(delegate);
            this.isDebugging = isDebugging;
        }

        @Override
        public boolean isDebugging() {
            return isDebugging;
        }
    }

    @Test public void disablesWhenDebugging() {
        TestRule rule = new ToggleableDisableOnDebug(new FailOnExecution(), true);
        final AtomicInteger value = new AtomicInteger();
        Statement incrementValue = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                value.incrementAndGet();
            }
        };
        rule.apply(incrementValue, null).evaluate();
        assertEquals(1, value.get());
    }

    @Test public void disablesWhenDebugging() {
        TestRule rule = new ToggleableDisableOnDebug(new FailOnExecution(), false);
        final AtomicInteger value = new AtomicInteger();
        Statement incrementValue = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                value.incrementAndGet();
            }
        };
        try {
            rule.apply(incrementValue, null).evaluate();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }
}

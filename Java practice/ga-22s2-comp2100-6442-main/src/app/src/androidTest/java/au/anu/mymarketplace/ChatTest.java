import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import MainActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ChatTest {

    /**
     * This class is built for Android UI Tests of Message interface.
     *
     * @author Zeyu Zhang u7394442
     */

    @Rule
    public final ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        /**
         * This setUp() void method is for set necessary things up before the execution of the test.
         * We keep it for API consistency.
         *
         * @author Zeyu Zhang u7394442
         */
        //Before Test case execution

    }

    @Test
    public void layoutTest() throws InterruptedException {
        /**
         * Test case: This is the UI test of the message interface
         */

        onView(withId(R.id.navigation_message)).perform(click());

        onView(withId(R.id.chat)).check(matches(isDisplayed()));

        onView(withId(R.id.blacklist)).check(matches(isDisplayed()));

        Thread.sleep(500);
    }



    @After
    public void tearDown() throws Exception {
        /**
         * We keep it for API consistency.
         */
        //After Test case Execution
    }


}


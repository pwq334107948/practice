import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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

public class CartTest {

    /**
     * This class is built for Android UI Tests of Cart interface and function.
     *
     * @author Zeyu Zhang u7394442
     */

    @Rule
    public final ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);


    private static Matcher<View> getElementFromMatchAtPosition(final Matcher<View> matcher, final int position) {
        /**
         * This helper function is designed for clicking, when there are multiple identical views.
         * Otherwise the espresso would have no idea which one you're clicking on.
         */
        return new BaseMatcher<View>() {
            int counter = 0;
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == position) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at hierarchy position "+position);
            }
        };
    }



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
    public void autoCheckOut() throws InterruptedException {
        /**
         * Test case: simulate the add items to cart, and get all items checked out.
         */
        onView(withId(R.id.searchEditor)).perform(clearText(), typeText("HP")).perform(closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());

        onView(withId(R.id.navigation_cart)).perform(click());

        Thread.sleep(500);

        onView(withId(R.id.navigation_search)).perform(click());

        Thread.sleep(20000);

        ViewInteraction itemClick = onView(
                allOf(
                        getElementFromMatchAtPosition(allOf(withId(R.id.item_image)), 2),
                        isDisplayed()));
        itemClick.perform(click());
        Thread.sleep(10000);

        onView(withId(R.id.addCartButton)).perform(click());
        Thread.sleep(100);

        onView(withId(R.id.iv_finish)).perform(click());
        Thread.sleep(100);

        onView(withId(R.id.navigation_cart)).perform(click());
        Thread.sleep(100);

        onView(withId(R.id.navigation_message)).perform(click());
        Thread.sleep(10000);

        onView(withId(R.id.navigation_cart)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.select_all_box)).perform(click());
        Thread.sleep(100);

        onView(withId(R.id.checkout)).perform(click());
        Thread.sleep(3000);

    }


    @After
    public void tearDown() throws Exception {
        /**
         * We keep it for API consistency.
         */
        //After Test case Execution
    }


}


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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
public class SearchTest {

    /**
     * This class is built for Android UI Tests of Search interface and function.
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
    public void test1ContainerViewTest() {
        /**
         * Test case: correctness of Container View in the layout
         */
        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }

    @Test
    public void test2NavigateBarTest() {
        /**
         * Test case: correctness of Navigate Bar in the layout
         */
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }

    @Test
    public void test3SearchEditorTest() {
        /**
         * Test case: correctness of Search Edit Text in the layout
         */
        onView(withId(R.id.searchEditor)).check(matches(isDisplayed()));
    }

    @Test
    public void test4SearchButtonTest() {
        /**
         * Test case: correctness of Search Button in the layout
         */
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test5SearchBoxTest() {
        /**
         * Test case: correctness of Search Box in the layout
         */
        onView(withId(R.id.searchBox)).check(matches(isDisplayed()));
    }

    @Test
    public void test6SearchListTest() {
        /**
         * Test case: correctness of Search List in the layout
         */
        onView(withId(R.id.searchList)).check(matches(isDisplayed()));
    }

    @Test
    public void test7AdvTest() throws InterruptedException {
        /**
         * Test case: Test the functional correctness of searching via simulation
         */
        onView(withId(R.id.searchEditor)).perform(clearText(), typeText("HP")).perform(closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.navigation_cart)).perform(click());

        Thread.sleep(500);

        onView(withId(R.id.navigation_search)).perform(click());

        Thread.sleep(20000);
        onView(withId(R.id.searchEditor)).perform(clearText(), typeText("Box")).perform(closeSoftKeyboard());
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.navigation_cart)).perform(click());

        Thread.sleep(500);

        onView(withId(R.id.navigation_search)).perform(click());

        Thread.sleep(20000);;
    }



    @After
    public void tearDown() throws Exception {
        /**
         * We keep it for API consistency.
         */
        //After Test case Execution
    }

}

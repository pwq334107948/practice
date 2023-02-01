import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

import LoginActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {

    /**
     * This class is built for Android UI Tests of Login Interface.
     * The whole test is divided into 6 blocks, which covers from
     * testing the layout of the UI, and also the function of register
     * and login.
     *
     * @author Zeyu Zhang u7394442
     */

    @Rule
    public final ActivityTestRule<LoginActivity> mRule = new ActivityTestRule<>(LoginActivity.class);
//    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);
//    private View decorView;

    @Before
    public void setUp() throws Exception {
        /**
         * This setUp() void method is for set necessary things up before the execution of the test.
         * It was originally designed for the deriving the decorView of toast message tests.
         * For more details, see test5registerTest() below.
         *
         * @author Zeyu Zhang u7394442
         */
        //Before Test case execution

//    public void setUp(){
//        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
//            @Override
//            public void perform(LoginActivity activity) {
//                decorView = activity.getWindow().getDecorView();
//            }
//        });
    }

    @Test
    public void test1UsernameTest() {
        /**
         * Test case: correctness of username's text view in the layout
         */
        onView(withId(R.id.oldPassword)).check(matches(withText("Username")));
    }

    @Test
    public void test2UsernameEnterTest() {
        /**
         * Test case: correctness of username's edit view in the layout
         */
        onView(withId(R.id.editor_Password)).check(matches(withHint("Please enter username")));
    }

    @Test
    public void test3PasswordTest() {
        /**
         * Test case: correctness of password's text view in the layout
         */
        onView(withId(R.id.Password)).check(matches(withText("Password")));
    }

    @Test
    public void test4PasswordEnterTest() {
        /**
         * Test case: correctness of password's edit view in the layout
         */
        onView(withId(R.id.edit_Password)).check(matches(withHint("Please enter password")));
    }

    @Test
    public void test5registerTest() {
        /**
         * Test case: functional correctness of register.
         * The test method simulates the operation of a user who try to register an account
         */
        onView(withId(R.id.editor_Password)).perform(clearText(), typeText("test@gmail.com"));
        onView(withId(R.id.edit_Password)).perform(clearText(), typeText("123456")).perform(closeSoftKeyboard());;

        onView(withId(R.id.registerButton)).perform(click());

        /** Remark: This block of commented code is for testing toast message after the user has registered
         * his/her account.
         * The reason why the block has been commented out is that toast message testing via Espresso
         * is not available to Android 11 API 30, when using "targetSdkVersion 30" and "compileSdkVersion 30",
         * or above. For more, see the github issue of official Android Test project:
         * https://github.com/android/android-test/issues/803
         * Since we are using the latest API 33, the purpose we keep the code is to make the API consistent
         *
         * @author Zeyu Zhang u7394442
         */
//        LoginActivity activity = mRule.getActivity();
//        onView(withText("Authentication failed."))
//                .inRoot(withDecorView(Matchers.not(Matchers.is(activity.getWindow().getDecorView()))))
//                .check(matches(isDisplayed()));


    }

    @Test
    public void test6loginTest() throws InterruptedException {
        /**
         * Test case: functional correctness of login.
         * The test method simulates the operation of a user who try to login an account,
         * and detects if the APP has successfully logged-in to the main interface.
         */
        onView(withId(R.id.editor_Password)).perform(clearText(), typeText("test@gmail.com"));
        onView(withId(R.id.edit_Password)).perform(clearText(), typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.LoginButton)).perform(click());
        Thread.sleep(10000);
        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }

}

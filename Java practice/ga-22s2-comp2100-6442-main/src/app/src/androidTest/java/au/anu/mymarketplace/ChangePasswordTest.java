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

import ChangePasswordActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ChangePasswordTest {



    @Rule
    public final ActivityTestRule<ChangePasswordActivity> mRule = new ActivityTestRule<>(ChangePasswordActivity.class);

    @Before
    public void setUp() throws Exception {
        //Before Test case execution
    }

    @Test
    public void test1OldPassword() {
        onView(withId(R.id.oldPassword)).check(matches(withText("Old password")));

    }

    @Test
    public void test2EditorPassword() {
        onView(withId(R.id.editor_Password)).check(matches(withHint("enter current password")));

    }

    @Test
    public void test3NewPassword() {
        onView(withId(R.id.newPassword)).check(matches(withText("New password")));

    }

    @Test
    public void test4EditorNewPassword() {
        onView(withId(R.id.editor_new_password)).check(matches(withHint("enter new password")));

    }

    @Test
    public void test5CheckPassword() {
        onView(withId(R.id.checkPassword)).check(matches(withText("Check password")));

    }

    @Test
    public void test6EditorCheckPassword() {
        onView(withId(R.id.editor_check_password)).check(matches(withHint("new password again")));

    }

    @Test
    public void test7ConfirmButton() {
        onView(withId(R.id.confirm_button)).check(matches(isDisplayed()));

    }

    @Test
    public void test8iv_finish() {
        onView(withId(R.id.iv_finish)).check(matches(isDisplayed()));

    }


    @Test
    public void test9AdvChangePassword() throws InterruptedException {
        onView(withId(R.id.editor_Password)).perform(clearText(), typeText("123456"));
        onView(withId(R.id.editor_new_password)).perform(clearText(), typeText("123456"));
        onView(withId(R.id.editor_check_password)).perform(clearText(), typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.confirm_button)).perform(click());
        Thread.sleep(10000);
    }



    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}

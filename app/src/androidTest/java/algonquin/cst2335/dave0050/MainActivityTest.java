package algonquin.cst2335.dave0050;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    //* Test to determine if password is missing uppercase, that the program will return text "You shall not pass!" *//

    @Test
    public void missingUpperCaseTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.Password));

        appCompatEditText.perform(replaceText("vin123!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.Login));

        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.TV));

        textView.check(matches(withText("You shall not pass!")));
    };


    //* Test to determine if password is missing lowercase, that the program will return text "You shall not pass!" *//
    @Test
    public void missingLowerCaseTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.Password));

        appCompatEditText.perform(replaceText("VIN123!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.Login));

        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.TV));

        textView.check(matches(withText("You shall not pass!")));
    };


    //* Test to determine if password is missing a number, that the program will return text "You shall not pass!" *//
    @Test
    public void missingNumberTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.Password));

        appCompatEditText.perform(replaceText("Vin!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.Login));

        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.TV));

        textView.check(matches(withText("You shall not pass!")));
    };


    //* Test to determine if password is missing special number, that the program will return text "You shall not pass!" *//
    @Test
    public void missingSpecialNumberTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.Password));

        appCompatEditText.perform(replaceText("Vin123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.Login));

        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.TV));

        textView.check(matches(withText("You shall not pass!")));
    };


    //* Test to determine if password is all password requirements are met, that the program will return text "Your password meets the requirements" *//
    @Test
    public void meetAllRequirementsTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.Password));

        appCompatEditText.perform(replaceText("Vin123!"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.Login));

        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.TV));

        textView.check(matches(withText("Your password meets the requirements")));
    };



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

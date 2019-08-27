package com.test.nike.dictionary;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.test.nike.is", appContext.getPackageName());
    }

    @Test
    public void isSearchBarDisplayed() {
        onView(withId(R.id.edit_text_search)).check(matches(isDisplayed()));

    }

    @Test
    public void typeAndClickOnSearch() {
        onView(withId(R.id.edit_text_search)).perform(typeText("Thanks"));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.search_button)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDefinitionResultIsDisplayed() {
        onView(withText("An expression, derived from thank-you")).check(matches(isDisplayed()));
    }

    @Test
    public void scrollRecyclerView() {
        onView(withId(R.id.rv_definitions)).perform(scrollTo());
    }
}

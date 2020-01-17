package com.furqoncreative.kadesubs4


import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.furqoncreative.kadesubs4.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchBehaviorTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testItemNotFound() {

        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("persib"), pressImeActionButton())

        //menggunakan thread untuk mencegah error karena response api yang lama
        Thread.sleep(5000)

        onView(withId(R.id.empty_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun testItemFound() {
        onView(withId(R.id.action_search))
            .check(matches(isDisplayed()))

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Real"), pressImeActionButton())

        //menggunakan thread untuk mencegah error karena response api yang lama
        Thread.sleep(5000)

        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))


        onView(withId(R.id.list_match)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                3
            )
        )

        onView(isRoot()).perform(pressBack())

        onView(isAssignableFrom(EditText::class.java))
            .perform(clearText(), typeText("barcelona"), pressImeActionButton())

        //menggunakan thread untuk mencegah error karena response api yang lama
        Thread.sleep(5000)

        onView(withId(R.id.list_match))
            .check(matches(isDisplayed()))

    }

}
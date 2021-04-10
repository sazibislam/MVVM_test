package com.sazib.ksl

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

internal object MethodUtils {

    private fun idle1sec() = Thread.sleep(1000)

    fun matchDisplay(id: Int) {
        idle1sec()
        idle1sec()
        onView(withId(id)).check(
            matches(isDisplayed())
        )
        idle1sec()
    }

    fun clickAView(id: Int) {
        onView(withId(id))
            .perform(click())
        idle1sec()
    }

    fun scrollDown(id: Int) {
        onView(withId(id))
            .perform(ViewActions.swipeUp())
        idle1sec()
    }

    fun scrollUp(id: Int) {
        onView(withId(id))
            .perform(ViewActions.swipeDown())
        idle1sec()
    }

    fun dialogClick(ok: String) {
        idle1sec()
        onView(ViewMatchers.withText(ok)).inRoot(RootMatchers.isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
        idle1sec()
    }
}
package com.sazib.ksl

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sazib.ksl.TestUtils.sleep
import com.sazib.ksl.ui._registration.reset_pass.ResetPassActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ResetPassActivityTest {

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<ResetPassActivity> =
        ActivityTestRule(ResetPassActivity::class.java)

    @Test
    fun checkUserLogin() {
//        loginTestUser("01921952390", user.second)
        loginTestUser()
    }

    private fun loginTestUser(
        number: String,
        password: String
    ) {
        onView(withId(R.id.inputNumber)).perform(replaceText(number))
        onView(withId(R.id.inputPassword)).perform(replaceText(password))
        onView(withId(R.id.inputConfirmPassword)).perform(replaceText(password))
        onView(withId(R.id.tvConfirm)).perform(click())
        sleep()
        onView(withId(R.id.activity_successful)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }

    private fun loginTestUser() {
        onView(withId(R.id.tvLogin)).perform(click())
        sleep()
        onView(withId(R.id.activity_signin)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }
}
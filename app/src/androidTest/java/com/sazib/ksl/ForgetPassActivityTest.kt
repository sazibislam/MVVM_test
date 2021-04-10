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
import com.sazib.ksl.TestUtils.user
import com.sazib.ksl.ui._registration.signin.SigninActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ForgetPassActivityTest {

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<SigninActivity> =
        ActivityTestRule(SigninActivity::class.java)

    @Test
    fun checkUserLogin() {
        forgetTestUser(user.first)
    }

    private fun forgetTestUser(
        user_name: String
    ) {
        onView(withId(R.id.inputUserName)).perform(replaceText(user_name))
        onView(withId(R.id.tvRequest)).perform(click())
        sleep()
        onView(withId(R.id.activity_reset_pass)).check(
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
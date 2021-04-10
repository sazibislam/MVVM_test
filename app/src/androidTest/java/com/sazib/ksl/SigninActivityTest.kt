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
import com.sazib.ksl.ui._registration.signin.SigninActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SigninActivityTest {

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<SigninActivity> =
        ActivityTestRule(SigninActivity::class.java)

//    @Test
//    fun checkUserLogin() {
//        loginTestUser(user.first, user.second)
//    }

    @Test
    fun checkUserLogin() {
        signupTestUser()
    }

    private fun signupTestUser() {
        onView(withId(R.id.tvSignUp)).perform(click())
        sleep()
        onView(withId(R.id.activity_register)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }

    private fun loginTestUser(
        user_name: String,
        password: String
    ) {
        onView(withId(R.id.inputUserName)).perform(replaceText(user_name))
        onView(withId(R.id.inputPassword)).perform(replaceText(password))
        onView(withId(R.id.tvLogin)).perform(click())
        sleep()
        onView(withId(R.id.activity_edit_task)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }
}
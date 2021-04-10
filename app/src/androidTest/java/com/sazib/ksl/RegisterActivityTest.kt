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
import com.sazib.ksl.TestUtils.testUser
import com.sazib.ksl.ui._registration.signup.RegisterActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RegisterActivityTest {

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<RegisterActivity> =
        ActivityTestRule(RegisterActivity::class.java)

    @Test
    fun checkUserLogin() {
        signupTestUser(testUser.first, testUser.second, "s@z.com")
    }

    private fun signupTestUser(
        user_name: String,
        password: String,
        email: String
    ) {
        onView(withId(R.id.inputName)).perform(replaceText(user_name))
        onView(withId(R.id.inputPassword)).perform(replaceText(password))
        onView(withId(R.id.inputEmail)).perform(replaceText(email))
        onView(withId(R.id.btnSubmit)).perform(click())
        sleep()
        sleep()
        onView(withId(R.id.activity_signin)).check(ViewAssertions.matches(isDisplayed()))
    }
}
package com.sazib.ksl

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sazib.ksl.TestUtils.pressBack
import com.sazib.ksl.TestUtils.sleep
import com.sazib.ksl.TestUtils.testUser
import com.sazib.ksl.TestUtils.user
import com.sazib.ksl.ui._registration.walkthrough.WalkThroughActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WalkThroughActivityTest {
    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<WalkThroughActivity> =
        ActivityTestRule(WalkThroughActivity::class.java)

    @Test
    fun checkWalkThrough() {
        loginCheck()
        sleep()
        pressBack()
        signupTestUser()
        sleep()

        signupTestUser(testUser.first, testUser.second, "sazibislam1@gmail.com")
        sleep()

        loginTestUser(user.first, user.second)
        sleep()
        pressBack()

        forgetTestUser(user.first)
        sleep()

        resetPassTest("01921952390", user.second)
        sleep()

    }

    private fun resetPassTest(
        number: String,
        password: String
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.inputNumber))
            .perform(ViewActions.replaceText(number))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
            .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.inputConfirmPassword))
            .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.tvConfirm)).perform(ViewActions.click())
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_successful)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    private fun forgetTestUser(
        user_name: String
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.inputUserName))
            .perform(ViewActions.replaceText(user_name))
        Espresso.onView(ViewMatchers.withId(R.id.tvRequest)).perform(ViewActions.click())
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_reset_pass)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    private fun loginTestUser(
        user_name: String,
        password: String
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.inputUserName))
            .perform(ViewActions.replaceText(user_name))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
            .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.tvLogin)).perform(ViewActions.click())
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_edit_task)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    private fun signupTestUser(
        user_name: String,
        password: String,
        email: String
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.inputName))
            .perform(ViewActions.replaceText(user_name))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
            .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
            .perform(ViewActions.replaceText(email))
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit)).perform(ViewActions.click())
        sleep()
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_signin)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    private fun signupTestUser() {
        Espresso.onView(ViewMatchers.withId(R.id.tvSignUp)).perform(ViewActions.click())
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_register)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    private fun loginCheck() {
        Espresso.onView(ViewMatchers.withId(R.id.tvLogin)).perform(ViewActions.click())
        sleep()
        Espresso.onView(ViewMatchers.withId(R.id.activity_signin)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}
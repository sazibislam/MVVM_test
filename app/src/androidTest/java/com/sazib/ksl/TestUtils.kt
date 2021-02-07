package com.sazib.ksl

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.util.concurrent.TimeUnit

internal object TestUtils {

    var user = Pair("sazib", "123456")

    var testUser = Pair("sazib", "123456")

    fun sleep() = Thread.sleep(1000)

    fun hold(second: Long) {
        IdlingPolicies.setIdlingResourceTimeout(second, TimeUnit.SECONDS)
    }

    fun hold() {
        IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.SECONDS)
    }

    fun hasDrawable(): BoundedMatcher<View, ImageView> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has drawable")
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                return imageView.drawable != null
            }
        }
    }

    fun pressBack() {
        Espresso.onView(ViewMatchers.isRoot())
            .perform(ViewActions.pressBack())
    }

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }
}

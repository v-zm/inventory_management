package com.vivek.inventorymanagement.features.inventory.view

import android.view.View
import androidx.lifecycle.Lifecycle.State
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun mainActivity_openAppAndGoToEachStateAndCloseApp_AppShouldCloseWithoutError() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.moveToState(State.CREATED)
            scenario.moveToState(State.STARTED)
            scenario.moveToState(State.RESUMED)
            scenario.moveToState(State.DESTROYED)
        }
    }

    /**
     * Clicks on Filter text with Id(R.id.filter_text) and it should open Popup menu
     * Check for popup menu
     *
     * */

    @Test
    fun filterText_click_openPopUpMenu() {
        launchActivity<MainActivity>().use { scenario ->
            // Perform click on filter text
            Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
            // Check if popup menu is rendered and it have 4 items
            Espresso.onView(isMenuDropDownListView()).check(matches(ViewMatchers.hasChildCount(4)))


            Espresso.onView(ViewMatchers.withText("Price")).perform(click())
            val textFieldInteraction =
                Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
            val viewAssertion = matches(
                ViewMatchers.withHint("Search by price")
            )
            textFieldInteraction.check(viewAssertion)
        }
    }

    @Test
    fun popUpMenu_check() {
        launchActivity<MainActivity>().use { scenario ->
            Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
            Espresso.onView(ViewMatchers.withText("Price")).inRoot(RootMatchers.isPlatformPopup())
                .perform(
                    click()
                )
        }
    }

    @Test
    fun checkForPopUpMenu(){
        launchActivity<MainActivity>().use { scenario->
            Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
            Espresso.onView(isMenuDropDownListView()).check(matches(ViewMatchers.hasChildCount(4)))

        }
    }
    fun withPositionInMenuDropDownListView(position: Int): Matcher<View> {
        return Matchers.allOf(ViewMatchers.withParent(isMenuDropDownListView()), ViewMatchers.withParentIndex(position))
    }

    fun isMenuDropDownListView(): Matcher<View> {
        return Matchers.anyOf(
            ViewMatchers.withClassName(Matchers.equalTo("android.widget.MenuPopupWindow\$MenuDropDownListView")),
            ViewMatchers.withClassName(Matchers.equalTo("androidx.appcompat.widget.MenuPopupWindow\$MenuDropDownListView"))
        )
    }

}
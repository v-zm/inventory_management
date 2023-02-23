package com.vivek.inventorymanagement.features.inventory.view

import android.view.View
import androidx.lifecycle.Lifecycle.State
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.R
import org.hamcrest.Description
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


    /**
     * move to different states of MainActivity
     * */

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
     * check for progress bar
     * */

    @Test
    fun mainActivity_launch_InitialStatePass() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.moveToState(State.RESUMED)
            Espresso.onView(ViewMatchers.withId(R.id.inventory_search_bar))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
                .check(matches(ViewMatchers.withHint("Search by name")))

            Espresso.onView(ViewMatchers.withId(R.id.filter_text))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.explore_text))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.home_bottom_navigation_bar))
                .check(matches(ViewMatchers.isDisplayed()))


        }
    }


    @Test
    fun filterText_onClick_OpenPopupMenu() {
        Espresso.onView(ViewMatchers.withId(R.id.filter_text))
            .check(matches(ViewMatchers.isDisplayed()))
        // Perform click on filter text
        Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
        // Check if popup menu is rendered and it have 4 items
        Espresso.onView(isMenuDropDownListView()).check(matches(ViewMatchers.hasChildCount(4)))

    }

    @Test
    fun filterText_performClick_MenuItemsShouldExist() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.moveToState(State.RESUMED)
            Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
            Espresso.onView(ViewMatchers.withText(R.string.filter_option_price_text))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(
                    matches(ViewMatchers.isDisplayed())
                )
            Espresso.onView(ViewMatchers.withText(R.string.filter_option_name_text))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(
                    matches(ViewMatchers.isDisplayed())
                )
            Espresso.onView(ViewMatchers.withText(R.string.filter_option_no_filter_text))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(
                    matches(ViewMatchers.isDisplayed())
                )
            Espresso.onView(ViewMatchers.withText(R.string.filter_option_only_with_image_text))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(
                    matches(ViewMatchers.isDisplayed())
                )
        }
    }

    @Test
    fun priceFilterOption_performClick_searchTextFieldHintChange() {
        Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
            .check(matches(ViewMatchers.withHint("Search by name")))
        filterText_onClick_OpenPopupMenu()

        Espresso.onView(ViewMatchers.withText(R.string.filter_option_price_text))
            .inRoot(RootMatchers.isPlatformPopup()).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
            .check(matches(ViewMatchers.withHint("Search by price")))
    }

    @Test
    fun inventorySearchField_changeTextToItem_checkForRecyclerViewDataInListFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
            .perform(replaceText("Item"))
        Espresso.onView(ViewMatchers.withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.hasMinimumChildCount(1)))

    }

    @Test
    fun inventorySearchField_changeTextTo100_checkForRecyclerViewDataInListFragment() {
        filterText_onClick_OpenPopupMenu()

        Espresso.onView(ViewMatchers.withText(R.string.filter_option_price_text))
            .inRoot(RootMatchers.isPlatformPopup()).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
            .perform(replaceText("100"))
        Espresso.onView(ViewMatchers.withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.hasMinimumChildCount(1)))
    }


    /**
     * Clicks on Filter text with Id(R.id.filter_text) and it should open Popup menu
     * Check if popup menu is there with 4 child count
     *
     * */

//    @Test
//    fun filterText_click_openPopUpMenu() {
//        launchActivity<MainActivity>().use { scenario ->
//            // Perform click on filter text
//            Espresso.onView(ViewMatchers.withId(R.id.filter_text)).perform(click())
//            // Check if popup menu is rendered and it have 4 items
//            Espresso.onView(isMenuDropDownListView()).check(matches(ViewMatchers.hasChildCount(4)))
////            Espresso.onData(Matchers.anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(1).check(
////                matches(ViewMatchers.hasChildCount(2))
////            )
//
//            Espresso.onData(Matchers.`is`(Matchers.instanceOf(MenuItem::class.java))).atPosition(0)
//                .check(
//                    matches(ViewMatchers.withText("Name"))
//                )
//
//
//            Espresso.onView(ViewMatchers.withText("Price")).perform(click())
//            val textFieldInteraction =
//                Espresso.onView(ViewMatchers.withId(R.id.inventory_search_text_field))
//            val viewAssertion = matches(
//                ViewMatchers.withHint("Search by price")
//            )
//            textFieldInteraction.check(viewAssertion)
//        }
//    }


    fun withPositionInMenuDropDownListView(position: Int): Matcher<View> {
        return Matchers.allOf(
            ViewMatchers.withParent(isMenuDropDownListView()),
            ViewMatchers.withParentIndex(position)
        )
    }

    fun isMenuDropDownListView(): Matcher<View> {
        return Matchers.anyOf(
            ViewMatchers.withClassName(Matchers.equalTo("android.widget.MenuPopupWindow\$MenuDropDownListView")),
            ViewMatchers.withClassName(Matchers.equalTo("androidx.appcompat.widget.MenuPopupWindow\$MenuDropDownListView"))
        )
    }

    fun withItemContent(expectedText: String?): Matcher<Any?>? {
        checkNotNull(expectedText)
        return withItemContent(Matchers.equalTo(expectedText))
    }

    fun withItemContent(itemTextMatcher: Matcher<String?>): Matcher<Any?>? {
        checkNotNull(itemTextMatcher)
        return object : BoundedMatcher<Any?, String>(String::class.java) {
            override fun matchesSafely(iconRow: String): Boolean {
                return itemTextMatcher.matches(iconRow)
            }

            override fun describeTo(description: Description) {
                description.appendText("with item content: ")
                itemTextMatcher.describeTo(description)
            }
        }
    }

}
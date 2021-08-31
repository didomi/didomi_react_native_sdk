package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed
import org.junit.After

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersParamsTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        // EVENT ON_READY NOT SENT ON SUCCESSIVE TESTS,
        // HAVE TO WAIT TO BE SURE THAT THE SDK IS READY
        Thread.sleep(5000L)

        // Make sure view is ready before starting test
        waitForDisplayed(withText("RESET"))
    }

    @After
    fun tearDown() {
    }

    private fun testMethodCall(method: String, needToScroll: Boolean) {
        onView(withText(method.toUpperCase())).perform(scrollTo(), click())

        if (needToScroll)
            onView(withText(method.toUpperCase())).perform(swipeUp(), swipeUp(), swipeUp(), swipeUp(), swipeUp(), swipeUp(), swipeUp())
        waitForDisplayed(withText("$method-OK"))
    }

    //TODO FIND A WAY TO CHECK WITHOUT THE ID HARD SET
    @Test
    fun test_GetPurpose() {
        testMethodCall("getPurpose [ID = 'cookies']", true)
    }

    @Test
    fun test_GetText() {
        testMethodCall("getText [Key = '0']", true)
    }

    @Test
    fun test_GetTranslatedText() {
        testMethodCall("getTranslatedText [Key = '0']", true)
    }

    @Test
    fun test_GetUserConsentStatusForPurpose() {
        testMethodCall("getUserConsentStatusForPurpose [ID = 'cookies']", true)
    }

    @Test
    fun test_GetUserConsentStatusForVendor() {
        testMethodCall("getUserConsentStatusForVendor [ID = '0']", true)
    }

    @Test
    fun test_GetUserConsentStatusForVendorAndRequiredPurposes() {
        testMethodCall("getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']", true)
    }

    @Test
    fun test_GetEnabledVendors() {
        testMethodCall("getUserLegitimateInterestStatusForPurpose [ID = 'cookies']", true)
    }
}
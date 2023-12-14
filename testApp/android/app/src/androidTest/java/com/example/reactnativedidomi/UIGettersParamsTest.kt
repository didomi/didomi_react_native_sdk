package com.example.reactnativedidomi

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersParamsTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    //TODO FIND A WAY TO CHECK WITHOUT THE ID HARD SET
    @Test
    fun test_GetPurpose() {

        tapButton("getPurpose [ID = 'cookies']")

        // Android doesn't always keep the same order for the properties.
        assertTextContains("\"id\":\"cookies\"")
        assertTextContains("\"iabId\":\"1\"")
        assertTextContains("\"name\":\"Store and/or access information on a device\"")
        assertTextContains("\"description\":\"Cookies, device or similar online identifiers (e.g. login-based identifiers, randomly assigned identifiers, network based identifiers) together with other information (e.g. browser type and information, language, screen size, supported technologies etc.) can be stored or read on your device to recognise it each time it connects to an app or to a website, for one or several of the purposes presented here.\"")
        assertTextContains("\"illustrations\":[\"Most purposes explained in this notice rely on the storage or accessing of information from your device when you use an app or visit a website. For example, a vendor or publisher might need to store a cookie on your device during your first visit on a website, to be able to recognise your device during your next visits (by accessing this cookie each time).\"]")
    }

    @Test
    fun test_GetPurpose_illustrations() {

        tapButton("getPurpose [ID = 'cookies'] illustrations[0]")

        assertText("\"Most purposes explained in this notice rely on the storage or accessing of information from your device when you use an app or visit a website. For example, a vendor or publisher might need to store a cookie on your device during your first visit on a website, to be able to recognise your device during your next visits (by accessing this cookie each time).\"")
    }

    @Test
    fun test_GetVendor() {

        tapButton("getVendor [ID = '217']")

        // Android doesn't always keep the same order for the properties.
        assertTextContains("\"id\":\"217\"")
        assertTextContains("\"name\":\"2KDirect, Inc. (dba iPromote)\"")
        assertTextContains("\"purposeIds\":[\"cookies\",\"create_ads_profile\",\"select_personalized_ads\"")
        assertTextContains("\"specialPurposeIds\":[\"1\",\"2\"]")
        assertTextContains("\"deviceStorageDisclosureUrl\":\"https://sdk.privacy-center.org/tcf/v3/disclosures/217.json\"")
        assertTextContains("\"namespace\":\"iab\"")
        assertTextContains("\"usesNonCookieAccess\":\"false\"")
    }

    @Test
    fun test_GetPurpose_urls() {

        tapButton("getVendor [ID = '217'] urls[0]")

        assertTextContains("\"langId\":\"en\"")
        assertTextContains("\"privacy\":\"https://www.ipromote.com/privacy-policy/\"")
        assertTextContains("\"legIntClaim\":\"https://www.ipromote.com/privacy-policy/\"")
    }

    @Test
    fun test_GetText() {
        tapButton("getText [Key = '0']")
        assertText("{}")
    }

    @Test
    fun test_GetTranslatedText() {
        tapButton("getTranslatedText [Key = '0']")
        assertText("\"0\"")
    }

    @Test
    fun test_GetUserConsentStatusForPurpose() {
        agreeToAll()

        tapButton("getUserConsentStatusForPurpose [ID = 'cookies']")
        assertText("true")
    }

    @Test
    fun test_GetUserConsentStatusForVendor() {
        agreeToAll()

        tapButton("getUserConsentStatusForVendor [ID = '217']")
        assertText("true")
    }

    @Test
    fun test_GetUserConsentStatusForVendorAndRequiredPurposes() {
        agreeToAll()

        tapButton("getUserConsentStatusForVendorAndRequiredPurposes [ID = '217']")
        assertText("true")
    }

    @Test
    fun test_GetEnabledVendors() {
        agreeToAll()

        tapButton("getUserLegitimateInterestStatusForPurpose [ID = 'cookies']")
        assertText("true")
    }

    @Test
    fun test_GetJavaScriptForWebViewWithExtra() {
        tapButton("getJavaScriptForWebViewWithExtra")

        // Asserting the whole string can be tricky so we just assert the end of it.
        val expected = "console.log('extra JS!');});\"".trim()

        // There might be a delay to get this string.
        Thread.sleep(1_000L)
        assertTextEndsWith(expected)
    }
}

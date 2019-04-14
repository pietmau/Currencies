package com.pppp.currencies

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.pppp.currencies.app.CurrencyApp
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.view.CurrenciesActivity
import com.pppp.currencies.presentation.view.custom.CurrenciesViewHolder
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

private const val ITL = "ITL"
private const val GBP = "GBP"
private const val COUNTRY = "some_country"
private const val UK = "uk"

@RunWith(AndroidJUnit4::class)
class CurrenciesFragmentInstrumentedTest {
    private val currencies: MutableLiveData<List<Currency>> = MutableLiveData()
    private val itl = Currency(ITL, "", BigDecimal(1), COUNTRY, "www")
    private val gbp = Currency(GBP, "", BigDecimal(2), UK, "www")
    private val loading: MutableLiveData<Boolean> = MutableLiveData()

    @get:Rule
    var activityRule: ActivityTestRule<CurrenciesActivity> = object :
        ActivityTestRule<CurrenciesActivity>(CurrenciesActivity::class.java) {
        override fun beforeActivityLaunched() {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val app = instrumentation.targetContext.applicationContext as CurrencyApp
            val viewmodel = TestCurrenciesViewModel(currencies, loading)
            val builder = DaggerTestAppComponent.builder()
            app.component = builder.testAppModule(TestAppModule(viewmodel)).build()
        }
    }

    @Test
    fun when_starts_then_progress_shows() {
        onView(withId(R.id.progress)).check(matches(isDisplayed()))
    }

    @Test
    fun when_receives_currencies_then_progress_hides() {
        // WHEN
        loading.postValue(false)
        // THEN
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
    }

    @Test
    fun when_receives_currencies_then_shows() {
        //WHEN
        currencies.postValue(listOf(itl, gbp))
        // THEN
        checkItemAtPosition()
    }

    @Test
    fun when_updates_currencies_then_shows() {
        //WHEN
        currencies.postValue(listOf(itl, gbp))
        checkItemAtPosition()
        checkItemAtPosition(1, GBP, UK, "2.0000")
        // THEN
        currencies.postValue(
            listOf(
                itl.copy(amount = BigDecimal(3)),
                gbp.copy(amount = BigDecimal(4))
            )
        )
        // the first item des not get updated when we receive data from the network
        checkItemAtPosition()
        checkItemAtPosition(1, GBP, UK, "4.0000")
    }

    @Test
    fun when_click_then_moves_to_top() {
        //WHEN
        currencies.postValue(listOf(itl, gbp))
        // THEN
        checkItemAtPosition()
        checkItemAtPosition(1, GBP, UK, "2.0000")
        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CurrenciesViewHolder>(1, click()))
        checkItemAtPosition(0, GBP, UK, "2.0000")
        checkItemAtPosition(1)
    }

    @Test
    fun when_clicks_then_gets_focus() {
        //WHEN
        currencies.postValue(listOf(itl, gbp))
        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CurrenciesViewHolder>(1, click()))
        // THEN
        onView(withId(R.id.recycler)).check(matches(atPosition(1, not(editTextHasFocus()))))
        onView(withId(R.id.recycler)).check(matches(atPosition(0, editTextHasFocus())))
    }

    private fun checkItemAtPosition(
        position: Int = 0,
        symbol: String = ITL,
        country: String = COUNTRY,
        amount: String = "1.0000"
    ) {
        onView(withId(R.id.recycler))
            .check(matches(atPosition(position, hasDescendant(withText(symbol)))))
        onView(withId(R.id.recycler))
            .check(matches(atPosition(position, hasDescendant(withText(country)))))
        onView(withId(R.id.recycler))
            .check(matches(atPosition(position, hasDescendant(withText(amount)))))
    }
}

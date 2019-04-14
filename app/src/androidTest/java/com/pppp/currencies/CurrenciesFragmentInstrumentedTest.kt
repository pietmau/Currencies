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
    private val data: MutableLiveData<List<Currency>> = MutableLiveData()
    private val itl = Currency(ITL, "", BigDecimal(1), COUNTRY, "www")
    private val gbp = Currency(GBP, "", BigDecimal(2), UK, "www")

    @get:Rule
    var activityRule: ActivityTestRule<CurrenciesActivity> = object :
        ActivityTestRule<CurrenciesActivity>(CurrenciesActivity::class.java) {
        override fun beforeActivityLaunched() {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val app = instrumentation.targetContext.applicationContext as CurrencyApp
            val viewmodel = TestCurrenciesViewModel(data)
            val builder = DaggerTestAppComponent.builder()
            val appComponent = builder.testAppModule(TestAppModule(viewmodel)).build()
            app.component = appComponent
        }
    }

    @Test
    fun when_receives_then_shows() {
        //WHEN
        data.postValue(listOf(itl))
        // THEN
        checkItemAtPosition()
    }


    @Test
    fun when_click_then_movestoTop() {
        //WHEN
        data.postValue(listOf(itl, gbp))
        // THEN
        checkItemAtPosition()
        checkItemAtPosition(1, GBP, UK, "2.0000")
        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CurrenciesViewHolder>(1, click()))
        checkItemAtPosition(0, GBP, UK, "2.0000")
        checkItemAtPosition(1)
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

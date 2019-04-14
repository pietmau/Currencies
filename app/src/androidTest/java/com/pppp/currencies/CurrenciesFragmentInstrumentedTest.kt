package com.pppp.currencies

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.pppp.currencies.app.CurrencyApp
import com.pppp.currencies.data.pokos.Currency
import com.pppp.currencies.presentation.view.CurrenciesActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrenciesFragmentInstrumentedTest {
    private val data: MutableLiveData<List<Currency>> = MutableLiveData()

    @get:Rule
    var activityRule: ActivityTestRule<CurrenciesActivity> = object :
        ActivityTestRule<CurrenciesActivity>(CurrenciesActivity::class.java) {
        override fun beforeActivityLaunched() {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val app = instrumentation.targetContext.applicationContext as CurrencyApp
            val viewmodel = TestCurrenciesViewModel(data)
            val appComponent =
                DaggerTestAppComponent.builder().testAppModule(TestAppModule(viewmodel)).build()
            app.component = appComponent
        }
    }

    @Test
    fun useAppContext() {
        data.postValue(emptyList())
        // Context of the app under test.
        //    val appContext = InstrumentationRegistry.getTargetContext()
        //  assertEquals("com.pppp.currencies", appContext.packageName)
    }
}

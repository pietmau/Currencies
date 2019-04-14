package com.pppp.currencies

import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.pppp.currencies.app.CurrencyApp
import com.pppp.currencies.presentation.view.CurrenciesActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrenciesFragmentInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<CurrenciesActivity> = object :
        ActivityTestRule<CurrenciesActivity>(CurrenciesActivity::class.java) {
        override fun beforeActivityLaunched() {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val app = instrumentation.targetContext.applicationContext as CurrencyApp
            val appComponent = DaggerTestAppComponent.create()
//            modelSource = DetailSource()
//            val appModule = TestAppModule(detailModelSource = modelSource, imageLoader = MockLoader)
//            val component = builder.testAppModule(appModule).build()
//            app.component = component
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.pppp.currencies", appContext.packageName)
    }
}

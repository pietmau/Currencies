package com.pppp.currencies

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}

fun editTextHasFocus(): Matcher<View> {
    return object : BaseMatcher<View>() {
        override fun describeTo(description: Description?) {

        }

        override fun matches(item: Any?) =
            (item as View).findViewById<EditText>(R.id.amount_input).hasFocus()
    }
}

package me.cafecode.espresso

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher {

    companion object {

        @JvmStatic
        fun withItemCount(itemCount: Int): BoundedMatcher<View, RecyclerView> {

            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $itemCount")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    return item?.adapter?.itemCount == itemCount
                }
            }
        }

        /**
         * {@link https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e}
         * https://gist.github.com/RomainPiel/ec10302a4687171a5e1a
         */
        @JvmStatic
        fun withRecyclerView(@IdRes recyclerViewId: Int, targetPosition: Int, @IdRes targetViewId: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {

                var resource: Resources? = null

                override fun describeTo(description: Description?) {
                    description?.appendText("with id: ${resource?.getResourceName(recyclerViewId)}")
                }

                override fun matchesSafely(view: View?): Boolean {
                    resource = view?.resources

                    val recyclerView = view?.rootView?.findViewById<RecyclerView>(recyclerViewId)
                            ?: return false

                    val childView = recyclerView.findViewHolderForAdapterPosition(targetPosition)?.itemView
                            ?: return false

                    val targetView = childView.findViewById<View>(targetViewId)

                    return view == targetView
                }
            }
        }
    }
}
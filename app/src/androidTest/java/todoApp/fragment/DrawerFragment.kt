package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.isDisplayed
import com.atiurin.ultron.extensions.swipeLeft
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf

object DrawerFragment : FragmentInterface<DrawerFragment> {
    private val navHeaderContainer = withId(R.id.navigation_header_container)
    private val headerImage = withContentDescription("Tasks header image")
    private val statisticBtn = allOf(
        withId(R.id.statistics_fragment_dest),
        hasDescendant(withText(R.string.statistics_title))
    )
    private val taskListBtn = allOf(
        withId(R.id.tasks_fragment_dest),
        hasDescendant(withText(R.string.list_title))
    )

    fun openStatisticFrag() {
        statisticBtn.click()
    }

    fun openTaskListFrag() {
        taskListBtn.click()
    }

    fun closeDrawer(){
        isRoot().swipeLeft()
    }

    override fun fragIsDisplayed() = apply {
        allOf(navHeaderContainer, hasDescendant(headerImage)).isDisplayed()
    }
}
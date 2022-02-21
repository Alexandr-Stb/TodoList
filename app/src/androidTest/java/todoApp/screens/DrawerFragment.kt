package todoApp.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.extensions.isDisplayed
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf
import todoApp.framework.FragDisplayed

object DrawerFragment:FragDisplayed<DrawerFragment> {
    private val navHeaderContainer = withId(R.id.navigation_header_container)
    private val headerImage = withContentDescription("Tasks header image")

    override fun fragIsDisplayed() = apply {
        allOf(navHeaderContainer, hasDescendant(headerImage)).isDisplayed()
    }
}
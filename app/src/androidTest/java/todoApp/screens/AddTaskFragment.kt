package todoApp.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf
import todoApp.framework.FragDisplayed

object AddTaskFragment : FragDisplayed<AddTaskFragment> {

    private val toolbar = withId(R.id.toolbar)
    private val toolbarText = withText(R.string.add_task)

    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarText))
    }
}
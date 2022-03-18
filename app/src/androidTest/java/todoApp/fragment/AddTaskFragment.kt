package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.isDisplayed
import com.atiurin.ultron.extensions.replaceText
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.Matchers.allOf

object AddTaskFragment : FragmentInterface<AddTaskFragment> {

    private val toolbar = withId(R.id.toolbar)
    private val toolbarText = withText(R.string.add_task)
    private val saveTaskBtn = withId(R.id.save_task_fab)
    private val titleTask = withId(R.id.add_task_title_edit_text)
    private val descriptionTask = withId(R.id.add_task_description_edit_text)
    private val navigateUpBtn = withContentDescription("Navigate up")
//    private val limitTask = withText(""")

    fun assertEmptyTask(){
        fragIsDisplayed()
    }

    fun returnBack(){
        navigateUpBtn.click()
    }


    fun createTask(task: Task){
        titleTask.replaceText(task.title)
        descriptionTask.replaceText(task.description)
        saveTaskBtn.click()
    }

    override fun fragIsDisplayed() = apply {
        allOf(toolbar, hasDescendant(toolbarText)).isDisplayed()
    }
}
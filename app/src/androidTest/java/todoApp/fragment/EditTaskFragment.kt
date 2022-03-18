package todoApp.fragment

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.isDisplayed
import com.atiurin.ultron.extensions.replaceText
import com.example.android.architecture.blueprints.todoapp.R
import org.hamcrest.Matchers.allOf
import todoApp.framework.TestData.countTasks
import todoApp.framework.TestData.editedTask
import todoApp.framework.TestData.getDB

object EditTaskFragment:FragmentInterface<EditTaskFragment> {
    private val toolbar = withId(R.id.toolbar)
    private val toolbarTitle = withText("Edit Task")
    private val titleTask = withId(R.id.add_task_title_edit_text)
    private val descriptionTask = withId(R.id.add_task_description_edit_text)
    private val recyclerView = withRecyclerView(withId(R.id.tasks_list))
    private val titleTaskInList = withId(R.id.title_text)
    private val navigateUp = withContentDescription("Navigate up")
    private val saveTaskBtn = withId(R.id.save_task_fab)

    fun editTask(){
        titleTask.replaceText(editedTask.title)
        descriptionTask.replaceText(editedTask.description)
    }

    fun assertEditedTask(){
        for(i in 0..countTasks){
            if(getDB()[i].title == editedTask.title&&
                    getDB()[i].description== editedTask.description)
                MainFragment.assertTaskInUI()

        }
    }

    fun clickSaveTaskBtn(){
        saveTaskBtn.click()
    }

    fun deleteTitle(){
        titleTask.replaceText("")
    }

    fun deleteDescription(){
        descriptionTask.replaceText("")
    }

    override fun fragIsDisplayed()= apply {
        allOf(toolbar,hasDescendant(toolbarTitle)).isDisplayed()
    }


}
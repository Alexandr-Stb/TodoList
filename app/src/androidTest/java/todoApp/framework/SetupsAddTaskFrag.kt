package todoApp.framework

import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.replaceText
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import todoApp.framework.TasksData.activeTask
import todoApp.screens.AddTaskFragment
import todoApp.screens.MainTaskScreen

object SetupsAddTaskFrag:SetupsFrag<SetupsAddTaskFrag> {
    private val addTaskBtn = withId(R.id.add_task_fab)
    private val titleTask = withId(R.id.add_task_title_edit_text)
    private val descriptionTask = withId(R.id.add_task_description_edit_text)
    private val saveTaskBtn = withId(R.id.save_task_fab)
    private val navigateUpBtn = withContentDescription("Navigate up")


    fun addTask(task:Task){
        titleTask.replaceText(task.title)
        descriptionTask.replaceText(task.description)
        saveTaskBtn.click()
    }

    fun closeFrag(){
        navigateUpBtn.click()
        MainTaskScreen.fragIsDisplayed()
    }
    override fun openFrag() = apply {
        addTaskBtn.click()
        AddTaskFragment.fragIsDisplayed()
    }
}
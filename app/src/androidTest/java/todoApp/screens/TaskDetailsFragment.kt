package todoApp.screens

import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.statistics.getActiveAndCompletedStats
import org.hamcrest.Matchers.allOf

import todoApp.framework.FragDisplayed

object TaskDetailsFragment:FragDisplayed<TaskDetailsFragment> {
    private val toolbar = withId(R.id.toolbar)
    private val toolbarTitle = withText("Task Details")
    private val checkBox = withId(R.id.task_detail_complete_checkbox)
    private val titleTask = withId(R.id.task_detail_title_text)
    private val descriptionTask = withId(R.id.task_detail_description_text)

    fun assertTaskDetails(task: Task) = apply{
        assertTaskText(task)
        if (task.isCompleted)
            checkBox.isChecked()
        else
            checkBox.isNotChecked()
    }

    private fun assertTaskText(task: Task) = apply{
        allOf(titleTask, withText(task.title))
            .isDisplayed()
        allOf(descriptionTask, withText(task.description))
            .isDisplayed()
    }

    override fun fragIsDisplayed()= apply {
        allOf(toolbar, hasDescendant(toolbarTitle)).isDisplayed()
    }

}
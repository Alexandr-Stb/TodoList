package todoApp.framework

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import todoApp.fragment.*
import todoApp.framework.TestData.countTasks
import kotlin.random.Random

object SetupsRepository {
    //Activity and recycler
    private val activityRule = ActivityTestRule(TasksActivity::class.java)
    private val recyclerView = withRecyclerView(R.id.tasks_list)

    //textView
    private val filterTitle = withId(R.id.title)
    private val titleTask = withId(R.id.add_task_title_edit_text)
    private val descriptionTask = withId(R.id.add_task_description_edit_text)

    //Btn view
    private val filterBtn = withId(R.id.menu_filter)
    private val filterAllTasksBtn = allOf(
        filterTitle, withText(R.string.nav_all)
    )
    private val filterActiveTasksBtn = allOf(
        filterTitle, withText(R.string.nav_active)
    )
    private val filterCompleteTasksBtn = allOf(
        filterTitle, withText(R.string.nav_completed)
    )
    private val clearCompletedBtn = allOf(
        withId(R.id.title), withText(R.string.menu_clear)
    )
    private val addTaskBtn = withId(R.id.add_task_fab)
    private val saveTaskBtn = withId(R.id.save_task_fab)
    private val menuBtn = withContentDescription("More options")
    private val completeCheckBoxBtn = withId(R.id.complete_checkbox)
    private val editTaskBtn = withId(R.id.edit_task_fab)
    private val openDrawerBtn = withContentDescription("Open navigation drawer")


    // universal functions
    fun deleteAllTask() {
        runActivity()
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .deleteAllTasks()
        }
    }

    fun createTask(task: Task) {
        runActivity()
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .saveTask(task)
        }
    }

    fun createManyTask() {
        runActivity()
        for (i in 0..countTasks) {
            if (i % 2 == 0) {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TestData.createTask(i, true))
                }
            } else {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TestData.createTask(i, false))
                }
            }
        }

    }

//statistics fragment
    fun createRandomCompletedTasks(){
        runActivity()
        var randomNum = 0
        for (i in 0..countTasks) {
            randomNum = Random.nextInt(0,2)
            if (randomNum == 0) {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TestData.createTask(i, true))
                }
            } else {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(TestData.createTask(i, false))
                }
            }
        }
    }

//main fragment
    fun filterActiveTasks() {
        runActivity()
        filterBtn.click()
        if (filterActiveTasksBtn.isSuccess { withTimeout(1).isDisplayed() })
            filterActiveTasksBtn.click()
        else {
            filterBtn.click()
            filterActiveTasksBtn.click()
        }
    }

    fun filterCompleteTasks() {
        runActivity()
        filterBtn.click()
        if (filterCompleteTasksBtn.isSuccess { withTimeout(500).isDisplayed() })
            filterCompleteTasksBtn.click()
        else {
            filterBtn.click()
            filterCompleteTasksBtn.click()
        }
    }

    fun filterAllTasks() {
        runActivity()
        filterBtn.click()
        if (filterAllTasksBtn.isSuccess { withTimeout(1).isDisplayed() })
            filterAllTasksBtn.click()
        else {
            filterBtn.click()
            filterAllTasksBtn.click()
        }
    }

    fun clearCompleted() {
        runActivity()
        menuBtn.click()
        clearCompletedBtn.click()
    }

    fun taskStateChange() {
        runActivity()
        recyclerView.firstItem().getChild(completeCheckBoxBtn).click()
    }

//addTask fragment
    fun addTask(task: Task) {
        titleTask.replaceText(task.title)
        descriptionTask.replaceText(task.description)
        saveTaskBtn.click()
    }

    //open fragments

    fun openDetailsFrag() {
        recyclerView.firstItem().click()
        TaskDetailsFragment.fragIsDisplayed()
    }

    fun openAddFragment() {
        addTaskBtn.click()
        AddTaskFragment.fragIsDisplayed()
    }

    fun openEditTaskFrag() {
        editTaskBtn.click()
        EditTaskFragment.fragIsDisplayed()
    }

    fun openDrawerFrag() {
        openDrawerBtn.click()
        DrawerFragment.fragIsDisplayed()
    }

    fun openStatisticsFrag() {
        openDrawerFrag()
        DrawerFragment.openStatisticFrag()
        StatisticFragment.fragIsDisplayed()
    }

    private fun runActivity() {
        activityRule.runOnUiThread {
            runBlocking {
                TasksRemoteDataSource.refreshTasks()
            }
        }
    }
}
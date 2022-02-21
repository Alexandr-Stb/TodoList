package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.framework.SetupsMainTaskFrag
import todoApp.framework.SetupsMainTaskFrag.createManyTask
import todoApp.framework.SetupsMainTaskFrag.createTask
import todoApp.framework.SetupsMainTaskFrag.deleteAllTask
import todoApp.framework.SetupsMainTaskFrag.runActivityInUiThread
import todoApp.framework.TasksData.activeTask
import todoApp.framework.TasksData.completeTask
import todoApp.screens.AddTaskFragment
import todoApp.screens.DrawerFragment
import todoApp.screens.MainTaskScreen
import todoApp.screens.TaskDetailsFragment

class TaskActivityTest {

    companion object {
        private const val DELETE_ALL_TASKS = "Delete all tasks"
        private const val CREATE_COMPLETE_TASK = "Create new complete task"
        private const val CREATE_ACTIVE_TASK = "Create new active task"
        private const val CREATE_MANY_TASK = "Create many task"
        private const val FILTER_ACTIVE = "Filter active task"
        private const val FILTER_COMPLETE = "Filter complete task"
    }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()


    private val activeOnTasks = SetUpRule()
        .add(DELETE_ALL_TASKS) {
            runActivityInUiThread(activityRule)
            deleteAllTask()

        }
        .add(CREATE_COMPLETE_TASK) {
            runActivityInUiThread(activityRule)
            createTask(completeTask)
        }
        .add(CREATE_ACTIVE_TASK) {
            runActivityInUiThread(activityRule)
            createTask(activeTask)
        }
        .add(CREATE_MANY_TASK) {
            runActivityInUiThread(activityRule)
            createManyTask()
        }
    private val actionsOnUi = SetUpRule()
        .add(FILTER_ACTIVE) {
            runActivityInUiThread(activityRule)
            SetupsMainTaskFrag.filterActiveTasks()
        }
        .add(FILTER_COMPLETE) {
            runActivityInUiThread(activityRule)
            SetupsMainTaskFrag.filterCompleteTasks()
        }


    init {
        ruleSequence.add(activeOnTasks, activityRule, actionsOnUi)
    }

// Open fragments tests

    @Test
    fun openAddTaskFragmentTest() {
        MainTaskScreen.openAddTaskFragment()
        AddTaskFragment.fragIsDisplayed()
    }

    @Test
    fun openDrawerTest() {
        MainTaskScreen.openDrawerFragment()
        DrawerFragment.fragIsDisplayed()
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_ACTIVE_TASK)
    @Test
    fun openTaskDetailsFragTest() {
        MainTaskScreen.openTaskDetails(activeTask)
        TaskDetailsFragment.fragIsDisplayed()
    }

    //Tasks Tests

    @SetUp(DELETE_ALL_TASKS, CREATE_ACTIVE_TASK)
    @Test
    fun assertTaskDisplayedTest() {
        MainTaskScreen.assertTaskDisplayed(activeTask)
    }

    //Filter Tests

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, FILTER_ACTIVE)
    @Test
    fun assertFilterActiveTasksTest() {
        MainTaskScreen.assertFilterTasks(false)
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, FILTER_COMPLETE)
    @Test
    fun assertFilterCompleteTasksTest() {
        MainTaskScreen.assertFilterTasks(true)
    }

}
package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.framework.SetupsMainTaskFrag
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
        private const val FILTER_ALL = "Filter all tasks"
        private const val ADD_TASK = "Add task thought add-task-fragment"
        private const val CLEAR_COMPLETED = "Clear completed Task"
        private const val TASK_STATE_CHANGE = "Task state change"
    }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()


    private val actionsBeforeUi = SetUpRule()
        .add(DELETE_ALL_TASKS) {
            SetupsMainTaskFrag.deleteAllTask()

        }
        .add(CREATE_COMPLETE_TASK) {
            SetupsMainTaskFrag.createTask(completeTask)
        }
        .add(CREATE_ACTIVE_TASK) {
            SetupsMainTaskFrag.createTask(activeTask)
        }
        .add(CREATE_MANY_TASK) {
            SetupsMainTaskFrag.createManyTask()
        }
    private val actionsOnUi = SetUpRule()
        .add(FILTER_ACTIVE) {
            SetupsMainTaskFrag.filterActiveTasks()
        }
        .add(FILTER_COMPLETE) {
            SetupsMainTaskFrag.filterCompleteTasks()
        }
        .add(FILTER_ALL) {
            SetupsMainTaskFrag.filterAllTasks()
        }
        .add(ADD_TASK) {
            SetupsMainTaskFrag.addTask()
        }
        .add(CLEAR_COMPLETED) {
            SetupsMainTaskFrag.clearCompleted()
        }
        .add(TASK_STATE_CHANGE) {
            SetupsMainTaskFrag.taskStateChange()
        }


    init {
        ruleSequence.add(actionsBeforeUi, activityRule, actionsOnUi)
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
        MainTaskScreen.openTaskDetails()
        TaskDetailsFragment.fragIsDisplayed()
    }

    //Tasks Tests

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK)
    @Test
    fun assertTaskDisplayedTest() {
        MainTaskScreen.assertTasksDisplayed()
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK)
    @Test
    fun taskStateChangeTest() {
        MainTaskScreen.assertTaskStateChange()
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, CLEAR_COMPLETED)
    @Test
    fun assertClearCompletedBtnTest() {
        MainTaskScreen.assertClearCompletedTasks()
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

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK)
    @Test
    fun assertFilterAllTasksTest() {
        MainTaskScreen.assertTasksDisplayed()
    }

    //DB Test

    @SetUp(ADD_TASK)
    @Test
    fun assertTaskInDBTest() {
        MainTaskScreen.compareTaskInUIAndDB()
    }
}

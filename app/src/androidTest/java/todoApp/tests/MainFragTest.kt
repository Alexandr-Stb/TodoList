package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.framework.SetupsRepository
import todoApp.framework.TestData.activeTask
import todoApp.framework.TestData.completeTask
import todoApp.fragment.AddTaskFragment
import todoApp.fragment.DrawerFragment
import todoApp.fragment.MainFragment
import todoApp.fragment.TaskDetailsFragment

class MainFragTest {

    companion object {
        private const val DELETE_ALL_TASKS = "Delete all tasks"
        private const val CREATE_COMPLETE_TASK = "Create new complete task"
        private const val CREATE_ACTIVE_TASK = "Create new active task"
        private const val CREATE_MANY_TASK = "Create many task"
        private const val FILTER_ACTIVE = "Filter active task"
        private const val FILTER_COMPLETE = "Filter complete task"
        private const val FILTER_ALL = "Filter all tasks"
        private const val CLEAR_COMPLETED = "Clear completed Task"
        private const val TASK_STATE_CHANGE = "Task state change"
    }

    private val actionsBeforeUi = SetUpRule()
        .add(DELETE_ALL_TASKS) {
            SetupsRepository.deleteAllTask()

        }
        .add(CREATE_COMPLETE_TASK) {
            SetupsRepository.createTask(completeTask)
        }
        .add(CREATE_ACTIVE_TASK) {
            SetupsRepository.createTask(activeTask)
        }
        .add(CREATE_MANY_TASK) {
            SetupsRepository.createManyTask()
        }
    private val actionsOnUi = SetUpRule()
        .add(FILTER_ACTIVE) {
            SetupsRepository.filterActiveTasks()
        }
        .add(FILTER_COMPLETE) {
            SetupsRepository.filterCompleteTasks()
        }
        .add(FILTER_ALL) {
            SetupsRepository.filterAllTasks()
        }
        .add(CLEAR_COMPLETED) {
            SetupsRepository.clearCompleted()
        }
        .add(TASK_STATE_CHANGE) {
            SetupsRepository.taskStateChange()
        }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)
    @get:Rule
    val ruleSequence = RuleSequence()

    init {
        ruleSequence.add(actionsBeforeUi, activityRule, actionsOnUi)
    }

// Open fragments tests

    @Test
    fun openAddTaskFragmentTest() {
        MainFragment.openAddTaskFragment()
        AddTaskFragment.fragIsDisplayed()
    }

    @Test
    fun openDrawerTest() {
        MainFragment.openDrawerFragment()
        DrawerFragment.fragIsDisplayed()

    }

    @SetUp(DELETE_ALL_TASKS, CREATE_ACTIVE_TASK)
    @Test
    fun openTaskDetailsFragTest() {
        MainFragment.openTaskDetails(activeTask)
        TaskDetailsFragment.fragIsDisplayed()
    }

    //Tasks Tests

    @SetUp(DELETE_ALL_TASKS, CREATE_COMPLETE_TASK)
    @Test
    fun assertTaskDisplayedTest() {
        MainFragment.assertTaskInUIAndDB(completeTask)
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK)
    @Test
    fun taskStateChangeTest() {
        MainFragment.assertTaskStateChange()
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, CLEAR_COMPLETED)
    @Test
    fun assertClearCompletedBtnTest() {
        MainFragment.assertClearCompletedTasks()
    }

    //Filter Tests

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, FILTER_ACTIVE)
    @Test
    fun assertFilterActiveTasksTest() {
        MainFragment.assertFilterTasks(false)
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK, FILTER_COMPLETE)
    @Test
    fun assertFilterCompleteTasksTest() {
        MainFragment.assertFilterTasks(true)
    }

    @SetUp(DELETE_ALL_TASKS, CREATE_MANY_TASK)
    @Test
    fun assertFilterAllTasksTest() {
        MainFragment.assertAllTasks()
    }
}

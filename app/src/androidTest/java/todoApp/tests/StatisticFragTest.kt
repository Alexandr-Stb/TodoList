package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.fragment.DrawerFragment
import todoApp.fragment.StatisticFragment
import todoApp.framework.SetupsRepository

class StatisticFragTest {
    companion object {
        private const val CREATE_RANDOM_TASKS = "Create tasks in a ratio of 2 to 5"
        private const val DELETE_ALL_TASKS = "Delete all task"
    }

    private val actionsBeforeUi = SetUpRule()
        .add(CREATE_RANDOM_TASKS){
            SetupsRepository.deleteAllTask()
            SetupsRepository.createRandomCompletedTasks()
        }
        .add(DELETE_ALL_TASKS){
            SetupsRepository.deleteAllTask()
        }

    private val actionsAfterUi = SetUpRule()
        .add {
            SetupsRepository.openStatisticsFrag()
        }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()

    init {
        ruleSequence.add(actionsBeforeUi, activityRule, actionsAfterUi)
    }

    @SetUp(CREATE_RANDOM_TASKS)
    @Test
    fun assertRandomCompleteTasks(){
        StatisticFragment.assertStatisticsTasks()
    }

    @SetUp(DELETE_ALL_TASKS)
    @Test
    fun assertEmptyTaskList(){
        StatisticFragment.assertStatisticsTasks()
    }

    @Test
    fun openDrawer(){
        StatisticFragment.openDrawer()
        DrawerFragment.fragIsDisplayed()
    }



}
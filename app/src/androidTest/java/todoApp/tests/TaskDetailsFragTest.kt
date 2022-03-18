package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.fragment.EditTaskFragment
import todoApp.framework.SetupsRepository
import todoApp.fragment.TaskDetailsFragment

class TaskDetailsFragTest {
    companion object{
        private const val CREATE_MANY_TASK = "Create and task"
        private const val OPEN_TASK = "Open this task"
    }

    private val actionsBeforeUi = SetUpRule()
        .add{
            SetupsRepository.deleteAllTask()
        }
        .add(CREATE_MANY_TASK){
            SetupsRepository.createManyTask()
        }

    private val actionsAfterUi = SetUpRule()
        .add(OPEN_TASK){
            SetupsRepository.openDetailsFrag()
        }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()

    init{
        ruleSequence.add(actionsBeforeUi,activityRule,actionsAfterUi)
    }

    @SetUp(CREATE_MANY_TASK)
    @Test
    fun assertTaskDetailsTest(){
        TaskDetailsFragment.openTask()
        TaskDetailsFragment.fragIsDisplayed()
        TaskDetailsFragment.assertTaskDisplayed()
    }

    @SetUp(CREATE_MANY_TASK, OPEN_TASK)
    @Test
    fun assertTaskDeleted(){
        TaskDetailsFragment.deleteTask()
        TaskDetailsFragment.assertDeleteTaskDB()
    }

    @SetUp(CREATE_MANY_TASK, OPEN_TASK)
    @Test
    fun assertChangeTaskState(){
        TaskDetailsFragment.changeStateTask()
        TaskDetailsFragment.assertTaskState()
    }

    @SetUp(CREATE_MANY_TASK, OPEN_TASK)
    @Test
    fun assertEditTaskFrag(){
        SetupsRepository.openEditTaskFrag()
        EditTaskFragment.fragIsDisplayed()
    }

}
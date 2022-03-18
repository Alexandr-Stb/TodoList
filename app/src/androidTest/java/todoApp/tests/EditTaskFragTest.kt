package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.fragment.EditTaskFragment
import todoApp.fragment.MainFragment
import todoApp.framework.SetupsRepository

class EditTaskFragTest {
    companion object {
        private const val OPEN_FIRST_TASK = "Open first task"
    }

    private val actionsBeforeUi = SetUpRule()
        .add {
            SetupsRepository.deleteAllTask()
            SetupsRepository.createManyTask()
        }
    private val actionsAfterUi = SetUpRule()
        .add(OPEN_FIRST_TASK) {
            SetupsRepository.openEditTaskFrag()
        }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()

    init {
        ruleSequence.add(actionsBeforeUi, activityRule, actionsAfterUi)
    }

    @SetUp(OPEN_FIRST_TASK)
    @Test
    fun assertEmptyTitleTask() {
        EditTaskFragment.deleteTitle()
        EditTaskFragment.clickSaveTaskBtn()
        EditTaskFragment.fragIsDisplayed()
    }

    @SetUp(OPEN_FIRST_TASK)
    @Test
    fun assertEmptyDescriptionTask(){
        EditTaskFragment.deleteDescription()
        EditTaskFragment.clickSaveTaskBtn()
        EditTaskFragment.fragIsDisplayed()
    }

    @SetUp(OPEN_FIRST_TASK)
    @Test
    fun assertAllElementsEmpty(){
        EditTaskFragment.deleteTitle()
        EditTaskFragment.deleteDescription()
        EditTaskFragment.clickSaveTaskBtn()
        EditTaskFragment.fragIsDisplayed()
    }

    @SetUp(OPEN_FIRST_TASK)
    @Test
    fun assertEditedTaskDisplayed(){
        EditTaskFragment.editTask()
        EditTaskFragment.clickSaveTaskBtn()
        MainFragment.fragIsDisplayed()
        EditTaskFragment.assertEditedTask()
    }
}
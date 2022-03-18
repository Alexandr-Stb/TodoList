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
import todoApp.framework.TestData.emptyDescriptionTask
import todoApp.framework.TestData.emptyTitleTask
import todoApp.framework.TestData.longTask
import todoApp.framework.TestData.simpleTask
import todoApp.framework.TestData.specifySymbolTask
import todoApp.fragment.AddTaskFragment
import todoApp.fragment.MainFragment

class AddTaskFragTest {
    companion object{
        private const val ADD_TASK = "Add task in add fragment"
        val task = activeTask
    }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    private val actionsInUi = SetUpRule()
        .add{
            SetupsRepository.openAddFragment()
        }
        .add(ADD_TASK){
            SetupsRepository.addTask(task)
        }

    @get:Rule
    val ruleSequence = RuleSequence()
    init{
        ruleSequence.add(activityRule,actionsInUi)
    }

    @SetUp(ADD_TASK)
    @Test
    fun assertTaskDisplayed(){
        MainFragment.assertTaskInUIAndDB(task)
    }

    @Test
    fun assertReturnBack(){
        AddTaskFragment.returnBack()
        MainFragment.fragIsDisplayed()
    }

    @Test
    fun assertEmptyTaskTest(){
        AddTaskFragment.assertEmptyTask()
    }

    @Test
    fun assertEmptyTitleTask(){
        AddTaskFragment.createTask(emptyTitleTask)
        AddTaskFragment.assertEmptyTask()
    }

    @Test
    fun assertEmptyDescriptionTask(){
        AddTaskFragment.createTask(emptyDescriptionTask)
        AddTaskFragment.assertEmptyTask()
    }

    @Test
    fun assertSimpleTask(){
        AddTaskFragment.createTask(simpleTask)
        MainFragment.fragIsDisplayed()
        MainFragment.assertTaskInUIAndDB(simpleTask)
    }

    @Test
    fun assertLongTask(){
        AddTaskFragment.createTask(longTask)
        MainFragment.fragIsDisplayed()
        MainFragment.assertTaskInUIAndDB(longTask)
    }

    @Test
    fun assertSpecifyTask(){
        AddTaskFragment.createTask(specifySymbolTask)
        MainFragment.fragIsDisplayed()
        MainFragment.assertTaskInUIAndDB(specifySymbolTask)
    }

}
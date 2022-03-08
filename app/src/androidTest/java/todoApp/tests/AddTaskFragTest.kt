package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.framework.SetupsAddTaskFrag
import todoApp.framework.TasksData.activeTask
import todoApp.framework.TasksData.completeTask
import todoApp.framework.TasksData.emptyDescriptionTask
import todoApp.framework.TasksData.emptyTitleTask
import todoApp.framework.TasksData.longTask
import todoApp.framework.TasksData.simpleTask
import todoApp.framework.TasksData.specifySymbolTask
import todoApp.screens.AddTaskFragment
import todoApp.screens.MainTaskScreen

class AddTaskFragTest {
    companion object{
        private const val ADD_TASK = "Add task in add fragment"
        val task = activeTask
    }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    private val actionsInUi = SetUpRule()
        .add{
            SetupsAddTaskFrag.openFrag()
        }
        .add(ADD_TASK){
            SetupsAddTaskFrag.addTask(task)
        }

    @get:Rule
    val ruleSequence = RuleSequence()
    init{
        ruleSequence.add(activityRule,actionsInUi)
    }

    @SetUp(ADD_TASK)
    @Test
    fun assertTaskDisplayed(){
        MainTaskScreen.assertTaskDisplayed(task)
    }

    @Test
    fun assertReturnBack(){
        AddTaskFragment.returnBack()
        MainTaskScreen.fragIsDisplayed()
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
        MainTaskScreen.fragIsDisplayed()
        MainTaskScreen.assertTaskDisplayed(simpleTask)
    }

    @Test
    fun assertLongTask(){
        AddTaskFragment.createTask(longTask)
        MainTaskScreen.fragIsDisplayed()
        MainTaskScreen.assertTaskDisplayed(longTask)
    }

    @Test
    fun assertSpecifyTask(){
        AddTaskFragment.createTask(specifySymbolTask)
        MainTaskScreen.fragIsDisplayed()
        MainTaskScreen.assertTaskDisplayed(specifySymbolTask)
    }

}
package todoApp.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import todoApp.fragment.DrawerFragment
import todoApp.fragment.MainFragment
import todoApp.fragment.StatisticFragment
import todoApp.framework.SetupsRepository

class DrawerFragTest {
    private val actionsBeforeUi = SetUpRule()
    private val actionsAfterUi = SetUpRule()
        .add{
            SetupsRepository.openDrawerFrag()
        }

    private val activityRule = ActivityTestRule(TasksActivity::class.java)

    @get:Rule
    val ruleSequence = RuleSequence()

    init {
        ruleSequence.add(actionsBeforeUi, activityRule, actionsAfterUi)
    }

    @Test
    fun assertOpenStatisticFrag(){
        DrawerFragment.openStatisticFrag()
        StatisticFragment.fragIsDisplayed()
    }

    @Test
    fun assertOpenTaskListFrag(){
        DrawerFragment.openTaskListFrag()
        MainFragment.fragIsDisplayed()
    }

    @Test
    fun assertCloseDrawerFrag(){
        DrawerFragment.closeDrawer()
        MainFragment.fragIsDisplayed()
    }
}
package todoApp.framework

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.ultron.extensions.click
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf

object SetupsMainTaskFrag {
    private val filterBtn = withId(R.id.menu_filter)
    private val filterTitle = withId(R.id.title)
    private val filterAllTasksBtn = allOf(
        filterTitle, withText(R.string.nav_all)
    )
    private val filterActiveTasksBtn = allOf(
        filterTitle, withText(R.string.nav_active)
    )

    private val filterCompletedTasksBtn = allOf(
        filterTitle,withText(R.string.nav_completed)
    )

    fun runActivityInUiThread(activityRule: ActivityTestRule<TasksActivity>) {
        activityRule.runOnUiThread {
            runBlocking {
                TasksRemoteDataSource.refreshTasks()
            }
        }
    }

    fun deleteAllTask() {
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .deleteAllTasks()
        }
    }

    fun createTask(task: Task){
        runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .saveTask(task)
        }
    }

    fun createManyTask(repeat:Int){
        for(i in 0..repeat){
            if(i%2==0) {
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(Task("Task:$i", "Description task $i", true))
                }
            }else{
                runBlocking {
                    ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                        .saveTask(Task("Task:$i", "Description task $i", false))
                }
            }
        }

    }

    fun filterActiveTasks(){
        filterBtn.click()
        filterActiveTasksBtn.click()
    }
}
package todoApp.framework

import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

object TestData {
    val completeTask = Task("Complete task", "This task is complete", true)
    val activeTask = Task("Active task", "This task is complete", false)
    val editedTask = Task("Edited task","Description edited task",false)
    const val countTasks = 10
    fun createTask(num:Int,isComplete:Boolean): Task {
        return Task("Task:$num", "Description task $num", isComplete,"Task:$num")
    }
    val simpleTask = Task("Simple task","This simple task")
    val longTask = Task("Long task,Long task,Long task,Long task","This long task,This long task,This long task,This long task,This long task,This long task,This long task,This long task")
    val specifySymbolTask = Task("?:#&(#&","#&()&*$@(#&(+_)+_&*(!@#?{|}{<><>")
    val emptyTitleTask = Task("","Description")
    val emptyDescriptionTask = Task("Title","")

    fun getDB(): List<Task> {
        val task = runBlocking {
            ServiceLocator.provideTasksRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .getTasks()
        }
        return (task as Result.Success).data
    }
}
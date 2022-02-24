package todoApp.framework

import com.example.android.architecture.blueprints.todoapp.data.Task

object TasksData {
    val completeTask = Task("Complete task", "This task is complete", true)
    val activeTask = Task("Active task", "This task is complete", false)
    const val countTasks = 28
    fun createTask(num:Int,isComplete:Boolean): Task {
        return Task("Task:$num", "Description task $num", isComplete,"Task:$num")
    }
}
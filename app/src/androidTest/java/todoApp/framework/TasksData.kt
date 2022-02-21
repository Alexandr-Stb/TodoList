package todoApp.framework

import com.example.android.architecture.blueprints.todoapp.data.Task

object TasksData {
    val completeTask = Task("Complete task", "This task is complete", true)
    val activeTask = Task("Active task", "This task is complete", false)
}
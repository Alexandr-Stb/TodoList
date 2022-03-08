package todoApp.framework

import com.example.android.architecture.blueprints.todoapp.data.Task

object TasksData {
    val completeTask = Task("Complete task", "This task is complete", true)
    val activeTask = Task("Active task", "This task is complete", false)
    const val countTasks = 10
    fun createTask(num:Int,isComplete:Boolean): Task {
        return Task("Task:$num", "Description task $num", isComplete,"Task:$num")
    }
    val simpleTask = Task("Simple task","This simple task")
    val longTask = Task("Long task,Long task,Long task,Long task","This long task,This long task,This long task,This long task,This long task,This long task,This long task,This long task")
    val specifySymbolTask = Task("?:#&(#&","#&()&*$@(#&(+_)+_&*(!@#?{|}{<><>")
    val emptyTitleTask = Task("","Description")
    val emptyDescriptionTask = Task("Title","")
}
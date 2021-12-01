package com.shenawynkov.tododemo.data.repo

import androidx.lifecycle.LiveData
import com.shenawynkov.tododemo.data.database.Todo
import com.shenawynkov.tododemo.data.database.TodoDatabase

class TodoRepo(private val db: TodoDatabase) {
    fun getAllTasks(): LiveData<List<Todo>> {
        return db.todoDao().getAllTodoList()
    }

    suspend fun addTask(todo: Todo) {
        db.todoDao().saveTodo(todo)
    }

    suspend fun updateTask(todo: Todo) {
        db.todoDao().updateTodo(todo)

    }

    suspend fun deleteTask(todo: Todo) {
        db.todoDao().deleteTodo(todo)

    }



}
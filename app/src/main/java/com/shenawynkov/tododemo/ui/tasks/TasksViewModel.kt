package com.shenawynkov.tododemo.ui.tasks


import androidx.lifecycle.ViewModel
import com.shenawynkov.tododemo.data.repo.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(repo: TodoRepo) : ViewModel() {

    var todoList = repo.getAllTasks()


}
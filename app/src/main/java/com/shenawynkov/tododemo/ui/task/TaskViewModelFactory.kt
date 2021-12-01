package com.shenawynkov.tododemo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.tododemo.data.repo.TodoRepo
import javax.inject.Inject

class TaskViewModelFactory
@Inject constructor(
    private val repo: TodoRepo
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(
            repo
        ) as T
    }


}
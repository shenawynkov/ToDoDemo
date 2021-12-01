package com.shenawynkov.tododemo.ui.task


import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.tododemo.data.database.Todo
import com.shenawynkov.tododemo.data.repo.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(val repo: TodoRepo) : ViewModel() {

    val previousTodo = MutableLiveData<Todo>()
    val taskName = MutableLiveData<String>()
    val taskCreator = MutableLiveData<String>()
    val taskDate = MutableLiveData<Date>()
    val actionDone = MutableLiveData<Boolean>(false)
    fun submit(view: View) {

        if (taskName.value == null || taskCreator.value == null || taskDate.value == null) {
            Toast.makeText(view.context, "please fill all date", Toast.LENGTH_LONG).show()
            return
        }
        else if(taskDate.value!! < Calendar.getInstance().time)
        {
            Toast.makeText(view.context, "please select a date in the future", Toast.LENGTH_LONG).show()
            return
        }

        if (previousTodo.value != null) {

            updateTodo(
                Todo(

                    taskName.value!!,
                    taskDate.value!!,
                    taskCreator.value!!
                )
            )
        } else {
            addTodo(
                Todo(

                    taskName.value!!,
                    taskDate.value!!,
                    taskCreator.value!!
                )
            )
        }


    }

    fun delete(view: View) {
        deleteTodo()
        actionDone.value = true

    }
    private fun deleteTodo()
    {
        if (previousTodo.value != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    repo.deleteTask(previousTodo.value!!)
                }
            }
        }
    }

    private fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                previousTodo.value?.let {
                    repo.deleteTask(it)
                }

                repo.addTask(
                    todo
                )
                actionDone.value = true


            }
        }

    }

    private fun addTodo(todo: Todo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.addTask(
                    todo
                )


                actionDone.value = true

            }

        }

    }
}
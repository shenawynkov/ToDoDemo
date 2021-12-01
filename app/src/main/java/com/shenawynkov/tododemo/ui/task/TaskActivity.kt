package com.shenawynkov.tododemo.ui.task

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.tododemo.R
import com.shenawynkov.tododemo.data.database.Todo
import com.shenawynkov.tododemo.databinding.ActivityTaskBinding
import dagger.hilt.android.AndroidEntryPoint


import java.util.*
import javax.inject.Inject
@AndroidEntryPoint
class TaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var binding: ActivityTaskBinding
    lateinit var viewModel: TaskViewModel

    @Inject
    lateinit var factory: TaskViewModelFactory

    companion object {
        const val Task = "TASK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        binding = DataBindingUtil.setContentView<ActivityTaskBinding>(
            this,
            R.layout.activity_task
        )

        viewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this
        getArguments()
        setUp()

    }

    private fun getArguments() {
        val todo = intent.getParcelableExtra<Todo>(Task)
        viewModel.previousTodo.value=todo
        viewModel.taskName.value=todo?.taskName
        viewModel.taskDate.value=todo?.taskDate
        viewModel.taskCreator.value=todo?.taskCreator
    }

    private fun setUp() {
        binding.edDate.setOnClickListener {
            val datePicker = com.shenawynkov.tododemo.utils.DatePicker()
            datePicker.show(supportFragmentManager, "DATE PICK")

        }
        viewModel.actionDone.observe(this, androidx.lifecycle.Observer {
            if(it==true)
            {
                finish()
            }
        })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.taskDate.value = mCalendar.time
    }
}
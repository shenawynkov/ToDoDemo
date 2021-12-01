package com.shenawynkov.tododemo.ui.tasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.tododemo.R
import com.shenawynkov.tododemo.base.GenericAdapter
import com.shenawynkov.tododemo.data.database.Todo
import com.shenawynkov.tododemo.databinding.ActivityMainBinding
import com.shenawynkov.tododemo.ui.task.TaskActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.app.job.JobScheduler

import android.app.job.JobInfo

import com.shenawynkov.tododemo.NetworkSchedulerService

import android.content.ComponentName
import android.content.Context

import android.os.Build

import androidx.annotation.RequiresApi




@AndroidEntryPoint
class TasksActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TasksViewModel

    @Inject
    lateinit var factory: TasksViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        viewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)
        binding.viewmodel = viewModel
        scheduleJob()
        setUp()
    }

    private fun setUp() {
        viewModel.todoList.observe(this, Observer {
            binding.rvTasks.apply {
                adapter = GenericAdapter(::moveToTask, it as ArrayList<*>, R.layout.item_todo)
                layoutManager=LinearLayoutManager(this@TasksActivity)
            }
        })
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this,TaskActivity::class.java))
        }
    }

    fun moveToTask(todo: Any) {
        if (todo is Todo) {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra(TaskActivity.Task, todo);
            startActivity(intent)
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleJob() {
        val myJob = JobInfo.Builder(0, ComponentName(this, NetworkSchedulerService::class.java))
            .setMinimumLatency(1000)
            .setOverrideDeadline(2000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(myJob)
    }
    override fun onStart() {
        super.onStart()
        // Start service and provide it a way to communicate with this class.
        val startServiceIntent = Intent(this, NetworkSchedulerService::class.java)
        startService(startServiceIntent)
    }
    override fun onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(Intent(this, NetworkSchedulerService::class.java))
        super.onStop()
    }
}
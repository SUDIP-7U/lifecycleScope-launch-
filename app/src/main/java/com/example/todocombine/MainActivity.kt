package com.example.todocombine

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var taskDao: TaskDao
    private lateinit var adapter: TaskAdapter
    private lateinit var taskList: LiveData<List<Task>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskDb = TaskDatabase.getDatabase(this)
        taskDao = taskDb.taskDao()

        val taskInput = findViewById<EditText>(R.id.task_input)
        val addBtn = findViewById<Button>(R.id.add_button)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)

        taskList = taskDao.getAllTasks()
        taskList.observe(this) { tasks ->
            adapter = TaskAdapter(tasks) { task -> deleteTask(task) }
            recyclerView.adapter = adapter
        }

        addBtn.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotBlank()) {
                addTask(Task(title = title))
                taskInput.text.clear()
            }
        }
    }

    private fun addTask(task: Task) {
        lifecycleScope.launch { taskDao.insert(task) }
    }

    private fun deleteTask(task: Task) {
        lifecycleScope.launch { taskDao.delete(task) }
    }
}

package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.demo.data.AppDatabase
import com.example.demo.repository.TodoRepository
import com.example.demo.ui.TodoDashboard
import com.example.demo.viewmodel.TodoViewModel
import com.example.demo.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(this)
        val repo = TodoRepository(db.todoDao())
        val factory = TodoViewModelFactory(repo)
        val todoViewModel: TodoViewModel by viewModels { factory }
        setContent {
            TodoDashboard(todoViewModel)
        }
    }
}

package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demo.data.AppDatabase
import com.example.demo.repository.TodoRepository
import com.example.demo.ui.TodoApp
import com.example.demo.viewmodel.TodoViewModel
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import com.example.demo.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(this)
        val repository = TodoRepository(db.todoDao())
        val factory = TodoViewModelFactory(repository)

        // Correct way
        val todoViewModel: TodoViewModel by viewModels { factory }

        setContent {
            TodoApp(todoViewModel)
        }
    }
}


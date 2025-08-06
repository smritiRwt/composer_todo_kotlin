package com.example.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demo.models.Todo
import com.example.demo.repository.TodoRepository
import com.example.demo.utils.getTodayDate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val todos: StateFlow<List<Todo>> = repository.getAllTodos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val todayTodos: StateFlow<List<Todo>> = repository.getTodosToday(getTodayDate())
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTodo(todo: Todo) = viewModelScope.launch { repository.insert(todo) }
    fun updateTodo(todo: Todo) = viewModelScope.launch { repository.update(todo) }
    fun deleteTodo(todo: Todo) = viewModelScope.launch { repository.delete(todo) }
}

class TodoViewModelFactory(private val repo: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == TodoViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demo.models.Todo
import com.example.demo.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class TodoFilter { ALL, DAY, WEEK, MONTH }

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _filterType = MutableStateFlow(TodoFilter.ALL)
    private val _filterValue = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val todos: StateFlow<List<Todo>> = combine(_searchQuery, _filterType, _filterValue)
    { query, filter, value ->
        Triple(query.trim(), filter, value)
    }.flatMapLatest { (query, filter, value) ->
        when {
            query.isNotBlank() -> repository.searchTodos(query)
            filter == TodoFilter.DAY -> repository.getTodosByDay(value)
            filter == TodoFilter.WEEK -> repository.getTodosByWeek(value)
            filter == TodoFilter.MONTH -> repository.getTodosByMonth(value)
            else -> repository.getAllTodos()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(type: TodoFilter, value: String = "") {
        _filterType.value = type
        _filterValue.value = value
    }

    fun addTodo(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch {
        repository.update(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        repository.delete(todo)
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == TodoViewModel::class.java) { "Unknown ViewModel class" }
        @Suppress("UNCHECKED_CAST")
        return TodoViewModel(repository) as T
    }
}

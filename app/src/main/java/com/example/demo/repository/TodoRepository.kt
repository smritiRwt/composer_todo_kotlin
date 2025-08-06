package com.example.demo.repository

import com.example.demo.data.TodoDao
import com.example.demo.models.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {
    fun getAllTodos(): Flow<List<Todo>> = dao.getAllTodos()
    fun getTodosToday(today: String): Flow<List<Todo>> = dao.getTodosToday(today)
    suspend fun insert(todo: Todo) = dao.insert(todo)
    suspend fun update(todo: Todo) = dao.update(todo)
    suspend fun delete(todo: Todo) = dao.delete(todo)
}

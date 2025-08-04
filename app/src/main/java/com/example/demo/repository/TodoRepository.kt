package com.example.demo.repository

import androidx.room.Query
import com.example.demo.data.TodoDao
import com.example.demo.models.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private  val dao: TodoDao) {
    fun getAllTodos():Flow<List<Todo>> = dao.getAllTodos()

    fun searchTodos(query: String):Flow<List<Todo>> = dao.searchTodos(query)

    fun getTodosByDay(query: String): Flow<List<Todo>> = dao.getTodosByDay(query)

    fun getTodosByMonth(query: String): Flow<List<Todo>> = dao.getTodosByMonth(query)

    fun getTodosByWeek(query: String):Flow<List<Todo>> = dao.getTodosByWeek(query)

    suspend fun insert(todo: Todo)=dao.insert(todo)

    suspend fun update(todo: Todo)= dao.update(todo)

    suspend fun  delete(todo: Todo)= dao.delete(todo)
}
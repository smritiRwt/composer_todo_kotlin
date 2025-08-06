// data/TodoDao.kt
package com.example.demo.data

import androidx.room.*
import com.example.demo.models.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY date, time")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE date = :today ORDER BY time")
    fun getTodosToday(today: String): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}

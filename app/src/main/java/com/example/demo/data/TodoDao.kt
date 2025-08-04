package com.example.demo.data

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.demo.models.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("select * from todo ORDER BY id desc")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchTodos(query: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE date=:day order by id desc")
    fun getTodosByDay(day:String):Flow<List<Todo>>


    @Query("select * from todo where strftime('%Y-%m',date)=:weekYear order by id desc")
    fun getTodosByWeek(weekYear:String):Flow<List<Todo>>

    @Query("select * from todo where strftime('%Y-%m',date)=:monthYear order by id desc")
    fun getTodosByMonth(monthYear:String):Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

}

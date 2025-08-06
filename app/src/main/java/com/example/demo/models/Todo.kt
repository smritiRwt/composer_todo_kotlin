package com.example.demo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val date: String = "", // yyyy-MM-dd
    val time: String = "", // e.g., 13:05 or 01:05 PM
    val status: String = "To-Do", // To-Do, Progress, Done
    val category: String = "General",
    val progress: Int = 0 // 0-100
)

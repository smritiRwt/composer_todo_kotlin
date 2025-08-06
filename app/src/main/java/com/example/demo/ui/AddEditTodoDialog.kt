package com.example.demo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.models.Todo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddEditTodoDialog(
    initialTodo: Todo?,
    onDismiss: () -> Unit,
    onSave: (Todo) -> Unit
) {
    var title by remember { mutableStateOf(initialTodo?.title ?: "") }
    var desc by remember { mutableStateOf(initialTodo?.description ?: "") }
    var category by remember { mutableStateOf(initialTodo?.category ?: "General") }
    var status by remember { mutableStateOf(initialTodo?.status ?: "To-Do") }
    var date by remember { mutableStateOf(initialTodo?.date ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    var time by remember { mutableStateOf(initialTodo?.time ?: "") }
    var progress by remember { mutableStateOf(initialTodo?.progress?.toString() ?: "0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialTodo == null) "Add Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status (To-Do/Progress/Done)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (yyyy-MM-dd)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time (e.g., 14:30)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = progress, onValueChange = { progress = it }, label = { Text("Progress (0-100)") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                        onSave(
                            Todo(
                                id = initialTodo?.id ?: 0,
                                title = title.trim(),
                                description = desc.trim(),
                                date = date.trim(),
                                time = time.trim(),
                                status = status.trim(),
                                category = category.trim(),
                                progress = progress.toIntOrNull() ?: 0
                            )
                        )
                    }
                }
            ) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

package com.example.demo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.models.Todo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTodoDialog(
    initialTodo: Todo?,
    onDismiss: () -> Unit,
    onSave: (Todo) -> Unit
) {
    var title by remember { mutableStateOf(initialTodo?.title ?: "") }
    var description by remember { mutableStateOf(initialTodo?.description ?: "") }
    var category by remember { mutableStateOf(initialTodo?.category ?: "General") }
    var date by remember { mutableStateOf(initialTodo?.date ?: LocalDate.now().format(DateTimeFormatter.ISO_DATE)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialTodo == null) "Add Todo" else "Edit Todo") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (yyyy-MM-dd)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
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
                                description = description.trim(),
                                isDone = initialTodo?.isDone ?: false,
                                category = category.ifBlank { "General" },
                                date = date.trim()
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

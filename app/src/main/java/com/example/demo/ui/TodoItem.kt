package com.example.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.demo.models.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
            .heightIn(min = 72.dp),
        elevation = 3.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (todo.isDone) Color.LightGray else Color.White)
                .padding(12.dp)
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = onCheckedChange
            )

            Spacer(Modifier.width(8.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    fontWeight = if (todo.isDone) FontWeight.Normal else FontWeight.Bold,
                    color = if (todo.isDone) Color.Gray else Color.Black
                )
                if (todo.description.isNotBlank()) {
                    Text(
                        text = todo.description,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                }
                Text(
                    text = "Category: ${todo.category}, Date: ${todo.date}",
                    style = MaterialTheme.typography.caption
                )
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task")
            }
        }
    }
}

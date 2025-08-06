package com.example.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.models.Todo
import com.example.demo.viewmodel.TodoViewModel

@Composable
fun TodoDashboard(viewModel: TodoViewModel) {
    val allTodos by viewModel.todos.collectAsState()
    val todayTodos by viewModel.todayTodos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }

    val todoCount = allTodos.count { it.status == "To-Do" }
    val progressCount = allTodos.count { it.status == "Progress" }
    val doneCount = allTodos.count { it.status == "Done" }
    val totalCount = allTodos.size

    var search by remember { mutableStateOf("") }
    // TODO: Optionally filter `allTodos`/`todayTodos` by search.

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FD))
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Top Bar
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Gray)
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color(0xFF486AF5),
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFECB3))
            )
        }
        Spacer(Modifier.height(18.dp))

        Text("Good Morning!", color = Color.Gray, fontSize = 16.sp)
        Spacer(Modifier.height(3.dp))
        Text(
            buildAnnotatedString {
                append("You have ")
                withStyle(SpanStyle(color = Color(0xFF466AFF), fontWeight = FontWeight.Bold)) {
                    append("$totalCount tasks ")
                }
                append("this month 👍")
            }, fontSize = 20.sp
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            placeholder = { Text("Search a task…") },
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(18.dp))
                .height(54.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent, backgroundColor = Color.White
            )
        )
        Spacer(Modifier.height(22.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StatChip("To-Do", todoCount, Color(0xFFFFEBEB))
            StatChip("Progress", progressCount, Color(0xFFFFF6E5))
            StatChip("Done", doneCount, Color(0xFFE7F6EF))
        }
        Spacer(Modifier.height(19.dp))

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            Text("Today's Tasks", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("See All", color = Color(0xFF466AFF), fontWeight = FontWeight.Medium)
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(13.dp)) {
            items(todayTodos) { todo ->
                TaskCardHorizontal(todo)
            }
        }
        Spacer(Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            DaysBar()
            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF466AFF)),
                modifier = Modifier.height(44.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                Text("Add Task", color = Color.White, modifier = Modifier.padding(start = 8.dp))
            }
        }
        Spacer(Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxHeight(0.8f)) {
            items(todayTodos) { todo ->
                TimelineTaskCard(todo)
            }
        }
    }

    if (showDialog) {
        AddEditTodoDialog(
            initialTodo = editingTodo,
            onDismiss = { showDialog = false },
            onSave = { todo ->
                if (todo.id == 0) viewModel.addTodo(todo) else viewModel.updateTodo(todo)
                showDialog = false
            }
        )
    }
}

@Composable
fun StatChip(title: String, count: Int, bg: Color) {
    Column(
        Modifier
            .background(bg, RoundedCornerShape(16.dp))
            .width(80.dp)
            .padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.Gray, fontSize = 13.sp)
        Text("$count", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun DaysBar() {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val dates = listOf("11", "12", "13", "14", "15", "16", "17")
    val selectedIdx = 3 // For demo, Thursday
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        days.zip(dates).forEachIndexed { idx, (day, date) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(day, color = if (idx == selectedIdx) Color(0xFF466AFF) else Color.Gray, fontSize = 13.sp)
                Text(date, color = if (idx == selectedIdx) Color(0xFF466AFF) else Color.Gray, fontWeight = FontWeight.Bold)
                if (idx == selectedIdx) {
                    Box(
                        Modifier
                            .padding(top = 2.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF466AFF))
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCardHorizontal(todo: Todo) {
    Card(
        Modifier
            .size(width = 166.dp, height = 110.dp),
        backgroundColor = when (todo.status) {
            "To-Do" -> Color(0xFF486AF5)
            "Progress" -> Color(0xFFED5764)
            "Done" -> Color(0xFF64D2A6)
            else -> Color.LightGray
        },
        shape = RoundedCornerShape(17.dp),
        elevation = 5.dp
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(todo.title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(todo.description, maxLines = 2, color = Color.White.copy(alpha = 0.94f), fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(todo.time, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(Modifier.weight(1f))
                LinearProgressIndicator(
                    progress = (todo.progress / 100f).coerceIn(0f, 1f),
                    modifier = Modifier.width(40.dp).height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = Color.White,
                    backgroundColor = Color.White.copy(alpha = 0.25f)
                )
                Text("${todo.progress}%", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Composable
fun TimelineTaskCard(todo: Todo) {
    Card(
        Modifier.fillMaxWidth(),
        backgroundColor = when (todo.status) {
            "To-Do" -> Color(0xFFFFEBEB)
            "Progress" -> Color(0xFFFFF6E5)
            "Done" -> Color(0xFFE7F6EF)
            else -> Color.White
        },
        shape = RoundedCornerShape(18.dp),
        elevation = 1.dp
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(todo.title, fontWeight = FontWeight.Bold)
                Text(todo.time, color = Color(0xFF466AFF), fontSize = 13.sp)
                Text(todo.description, color = Color.Gray, fontSize = 13.sp, maxLines = 2)
            }
        }
    }
}

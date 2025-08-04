package com.example.demo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.models.Todo
import com.example.demo.util.getCurrentMonthYear
import com.example.demo.util.getCurrentWeekYear
import com.example.demo.util.getTodayDate
import com.example.demo.viewmodel.TodoFilter
import com.example.demo.viewmodel.TodoViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoApp(viewModel: TodoViewModel) {
    val allTodos by viewModel.todos.collectAsState()
    val todayTodos = allTodos.filter { it.date == getTodayDate() }
    val pendingCount = allTodos.count { it.status == "To-Do" }
    val progressCount = allTodos.count { it.status == "Progress" }
    val doneCount = allTodos.count { it.status == "Done" }

    var search by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
            .padding(16.dp)
    ) {
        // Greeting and stat
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Good Morning, User!", color = Color.Gray)
                Row {
                    Text("You have ")
                    Text("${allTodos.size} tasks", color = Color(0xFF466AFF), fontWeight = FontWeight.Bold)
                    Text(" this month 👍", color = Color.Black)
                }
            }
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFECBC),
                elevation = 5.dp,
            ) {
                Icon(Icons.Default.Person, contentDescription = "Profile", modifier = Modifier.size(40.dp))
            }
        }

        Spacer(Modifier.height(20.dp))

        // Search Box
        OutlinedTextField(
            value = search,
            onValueChange = { value ->
                search = value
                viewModel.setSearchQuery(value)
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Search a task....") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        )

        Spacer(Modifier.height(24.dp))

        // Category Chips
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryChip("To-Do", pendingCount, Color(0xFFFCEEEE))
            CategoryChip("Progress", progressCount, Color(0xFFFFF5DB))
            CategoryChip("Done", doneCount, Color(0xFFE7F6EF))
        }

        Spacer(Modifier.height(24.dp))

        // Today's Tasks Horizontal List
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today's Tasks", fontWeight = FontWeight.Bold)
            TextButton(onClick = { /* See all Tasks */ }) { Text("See All") }
        }

        LazyRow {
            items(todayTodos) { todo ->
                TaskCardHorizontal(todo)
                Spacer(Modifier.width(12.dp))
            }
        }

        Spacer(Modifier.height(22.dp))

        // Timeline & Weekly Calendar
        TimelineSection(
            todos = todayTodos, // for demo; use your weekly/day-wise via ViewModel for real filter
            onAddTask = { /* open add dialog */ }
        )
    }
}

// --- SUPPORTING UI ELEMENTS ---
@Composable
fun CategoryChip(title: String, count: Int, bg: Color) {
    Column(
        Modifier
            .background(bg, shape = RoundedCornerShape(18.dp))
            .padding(16.dp)
            .width(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.DarkGray)
        Text("$count", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun TaskCardHorizontal(todo: Todo) {
    Card(
        Modifier
            .height(120.dp)
            .width(170.dp),
        backgroundColor = when (todo.status) {
            "To-Do" -> Color(0xFF466AFF)
            "Progress" -> Color(0xFFCFCFB5)
            "Done" -> Color(0xFFBEE5D3)
            else -> Color.LightGray
        },
        shape = RoundedCornerShape(18.dp),
        elevation = 5.dp
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(todo.title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(todo.description, maxLines = 2, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Text(todo.time, color = Color.White)
            LinearProgressIndicator(progress = todo.progress / 100f, Modifier.fillMaxWidth().height(4.dp), color = Color.White)
            Text("Progress ${todo.progress}%", color = Color.White, fontSize = 12.sp)
        }
    }
}

// --- Timeline Section ---
@Composable
fun TimelineSection(todos: List<Todo>, onAddTask: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        // Fake Weekly Bar
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            // Show e.g., Mon 11, Tue 12, ..., highlighting today
            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            days.forEachIndexed { i, day ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(day, color = if (i == 3) Color(0xFF466AFF) else Color.Gray)
                    Text("${11 + i}", color = if (i == 3) Color(0xFF466AFF) else Color.Gray)
                    if (i == 3) Box(Modifier.size(6.dp).background(Color(0xFF466AFF), CircleShape))
                }
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = onAddTask, shape = RoundedCornerShape(10.dp), elevation = ButtonDefaults.elevation(6.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF466AFF))) {
                Text("+ Add Task", color = Color.White)
            }
        }
        Spacer(Modifier.height(16.dp))
        // Timeline vertical
        LazyColumn {
            items(todos) { todo ->
                TimelineTaskCard(todo)
            }
        }
    }
}

@Composable
fun TimelineTaskCard(todo: Todo) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        backgroundColor = when (todo.status) {
            "To-Do" -> Color(0xFFFCEEEE)
            "Progress" -> Color(0xFFFFF5DB)
            "Done" -> Color(0xFFE7F6EF)
            else -> Color.White
        },
        elevation = 3.dp,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(todo.title, fontWeight = FontWeight.Bold)
                Text(todo.time, color = Color(0xFF466AFF), fontSize = 13.sp)
                Text(todo.description, color = Color.DarkGray, fontSize = 13.sp, maxLines = 2)
            }
        }
    }
}

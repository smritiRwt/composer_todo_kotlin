package com.example.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// For demo purposes; in real usage, map your actual data from ViewModel!
data class CardTask(val title: String, val time: String, val desc: String, val color: Color, val progress: Int = 100)
data class TimelineTask(val title: String, val desc: String, val time: String, val color: Color)

@Composable
fun TodoDashboard() {
    val dashboardBg = Color(0xFFF7F9FD)
    val circleBg = Color(0xFFFFECB3)

    val horizontalTasks = listOf(
        CardTask("Team Meeting 🙌", "10:00 AM", "Group discussion for the new product.", Color(0xFF486AF5), 48),
        CardTask("UI Design 🎉", "11:00 AM", "Make a homepage for the olakart app.", Color(0xFFED5764), 100)
    )

    val timelineTasks = listOf(
        TimelineTask("Wireframing", "Make some ideation from sketch and wireframes.", "12:00 PM", Color(0xFFED5764)),
        TimelineTask("UI Design", "Visual design from the wireframe and make design system.", "1:30 PM", Color(0xFF686DFF)),
        TimelineTask("Prototyping", "Make the interactive prototype for testing & stakeholders.", "3:00 PM", Color(0xFFFFCC57)),
        TimelineTask("Usability Testing", "Primary user testing and usability testing of the prototype.", "3:45 PM", Color(0xFF64D2A6)),
        TimelineTask("Meeting", "Sum up discussion for the new product with stakeholders.", "4:30 PM", Color(0xFFED5764)),
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(dashboardBg)
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        // Top Bar (Menu and Profile Pic)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Gray)
            Icon(
                Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(circleBg)
                    .border(2.dp, Color.White, CircleShape)
                    .padding(6.dp)
            )
        }
        Spacer(Modifier.height(18.dp))

        // Greeting and monthly stats
        Column(Modifier.fillMaxWidth()) {
            Text("Good Morning, Rifat!", color = Color.Gray, fontSize = 16.sp)
            Spacer(Modifier.height(3.dp))
            Text(
                buildAnnotatedString {
                    append("You have ")
                    withStyle(SpanStyle(color = Color(0xFF486AF5), fontWeight = FontWeight.Bold)) {
                        append("49 tasks ")
                    }
                    append("this month 👍")
                },
                fontSize = 20.sp
            )
        }
        Spacer(Modifier.height(16.dp))

        // Search Bar
        var search by remember { mutableStateOf("") }
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(18.dp))
                .height(56.dp),
            placeholder = { Text("Search a task…") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent, backgroundColor = Color.White
            )
        )

        Spacer(Modifier.height(22.dp))

        // Category Chips Row
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StatChip("To-Do", "12", Color(0xFFFFEBEB))
            StatChip("Progress", "8", Color(0xFFFFF6E5))
            StatChip("Done", "29", Color(0xFFE7F6EF))
        }

        Spacer(Modifier.height(24.dp))

        // Today's Tasks Title Row
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today's Tasks", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("See All", color = Color(0xFF486AF5), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(14.dp))

        // Horizontal List of cards
        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(horizontalTasks) { task ->
                TodayTaskCard(task)
            }
        }

        Spacer(Modifier.height(26.dp))

        // Weekly Calendar Row + Add Task Button
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            DaysBar()
            Button(
                onClick = { /* Add Task Action */ },
                modifier = Modifier.height(40.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF486AF5)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+ Add Task", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(14.dp))

        // Timeline
        LazyColumn(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(timelineTasks) { task ->
                TimelineTaskCard(task)
            }
        }
    }
}

@Composable
fun StatChip(title: String, count: String, bg: Color) {
    Column(
        Modifier
            .background(bg, RoundedCornerShape(14.dp))
            .width(80.dp)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.Gray, fontSize = 13.sp)
        Text(count, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun TodayTaskCard(task: CardTask) {
    Card(
        Modifier
            .size(width = 170.dp, height = 120.dp),
        backgroundColor = task.color,
        shape = RoundedCornerShape(18.dp),
        elevation = 6.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(task.title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                task.desc,
                maxLines = 2,
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 12.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(task.time, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(Modifier.weight(1f))
                LinearProgressIndicator(
                    progress = task.progress / 100f,
                    modifier = Modifier
                        .width(38.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color.White,
                    backgroundColor = Color.White.copy(alpha = 0.32f)
                )
                Text("${task.progress}%", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Composable
fun DaysBar() {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val dates = listOf("11", "12", "13", "14", "15", "16", "17")
    val selectedIdx = 3
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        days.zip(dates).forEachIndexed { idx, (day, date) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(day, color = if (idx == selectedIdx) Color(0xFF486AF5) else Color.Gray, fontSize = 13.sp)
                Text(date, color = if (idx == selectedIdx) Color(0xFF486AF5) else Color.Gray, fontWeight = FontWeight.Bold)
                if (idx == selectedIdx)
                    Box(
                        Modifier
                            .padding(top = 2.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF486AF5))
                    )
            }
        }
    }
}

@Composable
fun TimelineTaskCard(task: TimelineTask) {
    Card(
        Modifier.fillMaxWidth(),
        backgroundColor = task.color.copy(alpha = 0.11f),
        shape = RoundedCornerShape(17.dp),
        elevation = 0.dp
    ) {
        Row(
            Modifier.padding(vertical = 12.dp, horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(task.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = task.color)
                Text(task.time, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = task.color)
                Text(task.desc, color = Color.Gray, fontSize = 13.sp, maxLines = 2)
            }
        }
    }
}

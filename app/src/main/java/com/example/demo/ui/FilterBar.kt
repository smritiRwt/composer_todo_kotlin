package com.example.demo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.demo.viewmodel.TodoFilter

@Composable
fun FilterBar(selectedFilter: TodoFilter, onFilterSelected: (TodoFilter) -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        FilterChip("All", selectedFilter == TodoFilter.ALL) { onFilterSelected(TodoFilter.ALL) }
        FilterChip("Today", selectedFilter == TodoFilter.DAY) { onFilterSelected(TodoFilter.DAY) }
        FilterChip("This Week", selectedFilter == TodoFilter.WEEK) { onFilterSelected(TodoFilter.WEEK) }
        FilterChip("This Month", selectedFilter == TodoFilter.MONTH) { onFilterSelected(TodoFilter.MONTH) }
    }
}

@Composable
fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (selected) Color(0xFFBBDEFB) else Color.Transparent
        )
    ) {
        Text(text)
    }
}

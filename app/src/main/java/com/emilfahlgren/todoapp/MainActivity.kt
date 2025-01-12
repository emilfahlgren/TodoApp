package com.emilfahlgren.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.emilfahlgren.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TodoViewModel>()
//    private val days = listOf(
//        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel)

                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: TodoViewModel) {
    var newItemTitle by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf("Monday") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        val days = listOf(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        )

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 80.dp)
            ) {
                items(days) { day ->
                    ExpandableCard(title = day, content = {
                        val tasks = viewModel.todoMap.value[day] ?: emptyList()
                        tasks.forEach { task ->
                            TodoRow(task)
                        }
                    })
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = { viewModel.clearTodoItems() },
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear")
                }

                FloatingActionButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = newItemTitle,
                        label = { Text("Enter new task...") },
                        onValueChange = { newItemTitle = it },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    TextButton(onClick = { expanded = !expanded }) {
                        Text("Select Day: $selectedDay")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        days.forEach { day ->
                            DropdownMenuItem(onClick = {
                                selectedDay = day
                                expanded = false
                            }, text = { Text(text = day) })
                        }
                    }

                    Row {
                        TextButton(onClick = {
                            showDialog = false
                            if (newItemTitle.isNotBlank()) {
                                viewModel.addTodoItem(newItemTitle, selectedDay)
                                newItemTitle = ""
                            }
                        }) {
                            Text("Add", style = MaterialTheme.typography.bodyMedium)
                        }

                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

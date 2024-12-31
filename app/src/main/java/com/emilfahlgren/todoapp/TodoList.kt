package com.emilfahlgren.todoapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun TodoList(todoList: MutableList<TodoItem>) {
    LazyColumn {
        items(todoList) { item ->
            TodoRow(item)
        }
    }
}
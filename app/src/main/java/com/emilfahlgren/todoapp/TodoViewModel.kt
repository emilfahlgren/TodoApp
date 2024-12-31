package com.emilfahlgren.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    val todoList = mutableStateListOf<TodoItem>()

    fun addTodoItem(title: String) {
        todoList.add(TodoItem(title = title))
    }

    fun clearTodoItems() {
        todoList.removeAll{it.isChecked.value}
    }
}

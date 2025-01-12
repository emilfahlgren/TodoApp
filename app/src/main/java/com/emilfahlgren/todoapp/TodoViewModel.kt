package com.emilfahlgren.todoapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private val _todoMap = mutableStateOf<Map<String, List<TodoItem>>>(emptyMap())
    val todoMap: State<Map<String, List<TodoItem>>> = _todoMap

    fun addTodoItem(title: String, day: String) {
        val newItem = TodoItem(title = title, day = day)
        val currentDayTasks = _todoMap.value[day] ?: emptyList()
        val updatedTasks = currentDayTasks + newItem

        _todoMap.value = _todoMap.value.toMutableMap().apply {
            put(day, updatedTasks)
        }
    }

    fun clearTodoItems() {
        _todoMap.value = _todoMap.value.mapValues { (_, tasks) ->
            tasks.filterNot { it.isChecked.value }
        }
    }
}
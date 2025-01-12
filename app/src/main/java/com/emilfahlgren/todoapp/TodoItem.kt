package com.emilfahlgren.todoapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TodoItem(
    val title: String, val isChecked: MutableState<Boolean> = mutableStateOf(false), val day: String
)
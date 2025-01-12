package com.emilfahlgren.todoapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoRow(todoItem: TodoItem) {
    val textStyle = if (todoItem.isChecked.value) {
        TextStyle(textDecoration = TextDecoration.LineThrough)
    } else {
        TextStyle()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = todoItem.title,
            modifier = Modifier.weight(1f),
            style = textStyle,
            fontSize = 16.sp

        )
        Checkbox(checked = todoItem.isChecked.value, onCheckedChange = { checkedStatus ->
            todoItem.isChecked.value = checkedStatus
        })
    }
}
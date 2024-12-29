package com.emilfahlgren.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emilfahlgren.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                TodoList(
                    remember{mutableStateListOf(
                        TodoItem("Finish TodoApp", mutableStateOf(false)),
                        TodoItem("Start TodoApp", mutableStateOf(true))
                    )}
                )
            }
        }
    }
}

data class TodoItem(
    val title: String,
    val isChecked: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun TodoList(todoList: MutableList<TodoItem>) {
    LazyColumn {
        items(todoList) { item ->
            TodoRow(item)
        }
    }
}

@Composable
fun TodoRow(todoItem: TodoItem) {
    val textStyle =
        if (todoItem.isChecked.value) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = todoItem.title,
            modifier = Modifier.weight(1f),
            style = textStyle,
            fontSize = 20.sp

        )
        Checkbox(
            checked = todoItem.isChecked.value,
            onCheckedChange =  { checkedStatus ->
                todoItem.isChecked.value = checkedStatus
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    TodoAppTheme {
        TodoList(
            remember{mutableStateListOf(
                TodoItem("Finish TodoApp", mutableStateOf(false)),
                TodoItem("Start TodoApp", mutableStateOf(true))
            )}
        )
    }
}
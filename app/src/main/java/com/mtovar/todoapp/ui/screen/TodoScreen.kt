package com.mtovar.todoapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtovar.todoapp.model.TodoItem
import com.mtovar.todoapp.viewmodel.TodoViewModel

@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    val todoItems = viewModel.todoItems.collectAsState()
    var newTodoTitle by remember { mutableStateOf("") }


    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(16.dp)) {
        Text(
            text = "Lista de Tareas",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium
        )

        // Input field and ADD Buttton
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTodoTitle,
                onValueChange = { newTodoTitle = it },
                modifier = Modifier.weight(1f),
                label = { Text("Nueva tarea") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTodoTitle.isNotBlank()) {
                        viewModel.addTodo(newTodoTitle)
                        newTodoTitle = ""
                    }
                }

            ) {
                Text("Agregar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Todo list
        LazyColumn {
            items(todoItems.value.size) { item ->
                TodoItemRow(
                    item = todoItems.value[item],
                    onToggleComplete = { id -> viewModel.toggleTodo(id) },
                    onDelete = { id -> viewModel.deleteTodo(id) }
                )
            }
        }
    }
}

@Composable
fun TodoItemRow(
    item: TodoItem,
    onToggleComplete: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onToggleComplete(item.id) }
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Button(
            onClick = { onDelete(item.id) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Eliminar")
        }
    }
}

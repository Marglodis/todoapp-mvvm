package com.mtovar.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mtovar.todoapp.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {
    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems: StateFlow<List<TodoItem>> = _todoItems.asStateFlow()

    private var nextId = 0

    fun addTodo(title: String) {
        _todoItems.value = _todoItems.value + TodoItem(id = nextId++, title = title, false)
    }

    fun toggleTodo(id: Int) {
        _todoItems.value = _todoItems.value.map {
            if (it.id == id)
                it.copy(isCompleted = !it.isCompleted) else it

        }
    }

    fun deleteTodo(id: Int) {
        _todoItems.value = _todoItems.value.filter { it.id != id }
    }
}
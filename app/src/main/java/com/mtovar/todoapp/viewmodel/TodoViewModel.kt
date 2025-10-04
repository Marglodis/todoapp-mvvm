package com.mtovar.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mtovar.todoapp.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel() {
    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems: StateFlow<List<TodoItem>> = _todoItems.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    private var nextId = 0
    private var lastDeletedItem: TodoItem? = null


    fun addTodo(title: String) {
        _todoItems.value = _todoItems.value + TodoItem(id = ++nextId, title = title)
    }

    fun toggleComplete(id: Int) {
        _todoItems.value = _todoItems.value.map {
            if (it.id == id)
                it.copy(isCompleted = !it.isCompleted) else it

        }
    }

    fun deleteTodo(id: Int) {
        val itemToDelete = _todoItems.value.find { it.id == id }
        itemToDelete?.let {
            lastDeletedItem = it
            _todoItems.value = _todoItems.value.filter { it.id != id }
            _snackbarMessage.value = "Tarea eliminada: ${it.title}"
         }

        }

    fun undoDelete(){
        lastDeletedItem?.let { item ->
            _todoItems.value = (_todoItems.value + item).sortedBy { it.id }
            lastDeletedItem = null
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
        lastDeletedItem = null
    }
}

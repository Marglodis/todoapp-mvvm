package com.mtovar.todoapp.model

data class TodoItem(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

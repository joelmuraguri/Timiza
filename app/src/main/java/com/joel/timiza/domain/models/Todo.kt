package com.joel.timiza.domain.models

data class Todo(
    val id : Int,
    val title : String,
    val content : String,
    val status : Status
)

enum class Status{
    PENDING, COMPLETED
}
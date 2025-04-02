package com.joel.timiza.domain.models

data class User(
    val uid : String,
    val name : String,
    val email : String,
    val profileUrl : String? = "",
)

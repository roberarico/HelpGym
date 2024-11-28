package com.example.helpgym.Model

import java.io.Serializable

data class Exercise(
    val name: String = "",
    val series: String = "",
    val repeticiones: String = ""):Serializable{
    override fun toString(): String {
        return "$name,Sets: $series, Reps: $repeticiones, "
    }
    }

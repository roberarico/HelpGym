package com.example.helpgym.Model

import java.io.Serializable

data class Workout ( val day: String = "", val exercises: List<Exercise> = listOf()): Serializable

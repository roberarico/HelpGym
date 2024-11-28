package com.example.helpgym.Model

import java.io.Serializable

data class Rutina(
    val nombre: String = "", // Nombre de la rutina
    val dias: Map<String, ArrayList<Exercise>> = mapOf(
        "lunes" to ArrayList(),
        "martes" to ArrayList(),
        "miércoles" to ArrayList(),
        "jueves" to ArrayList(),
        "viernes" to ArrayList(),
        "sábado" to ArrayList(),
        "domingo" to ArrayList()
    )
) : Serializable

package com.example.helpgym

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.helpgym.Model.Exercise
import com.example.helpgym.Model.Rutina
import com.example.helpgym.databinding.ActivityNuevaRutinaBinding
import com.google.firebase.firestore.FirebaseFirestore

class NuevaRutina : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaRutinaBinding
    private lateinit var adapter: ArrayAdapter<Exercise>
    private var exercises: ArrayList<Exercise> = ArrayList()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val day = intent.getStringExtra("day") ?: "lunes" // Día predeterminado si no se pasa un extra

        // Restaurar la lista de ejercicios desde el estado guardado (si lo hay)
        if (savedInstanceState != null) {
            exercises = savedInstanceState.getSerializable("exercises") as ArrayList<Exercise>
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exercises)
        binding.listViewExercises.adapter = adapter

        // Agregar un nuevo ejercicio
        binding.buttonAddExercise.setOnClickListener {
            val exerciseName = binding.editTextExercise.text.toString()
            val sets = binding.editTextSets.text.toString()
            val reps = binding.editTextReps.text.toString()

            if (exerciseName.isNotBlank() && sets.isNotBlank() && reps.isNotBlank()) {
                val exercise = Exercise(exerciseName, sets, reps)
                exercises.add(exercise)
                adapter.notifyDataSetChanged()

                binding.editTextExercise.text.clear()
                binding.editTextSets.text.clear()
                binding.editTextReps.text.clear()
            }
        }

        // Confirmar la rutina y guardar en Firestore
        binding.confirmar.setOnClickListener {
            val nombreRutina = binding.editTextworkoutName.text.toString()
            if (nombreRutina.isBlank()) {
                Log.w(TAG, "El nombre de la rutina está vacío.")
                return@setOnClickListener
            }

            val ejerciciosDelDia = exercises.map { exercise ->
                mapOf(
                    "ejercicio" to exercise.name,
                    "series" to exercise.series,
                    "repeticiones" to exercise.repeticiones
                )
            }

            val rutinaId = db.collection("rutinas").document().id // Generar ID único
            val rutinaData = mapOf(
                "nombre" to nombreRutina,
                "dias" to mapOf(
                    day to ejerciciosDelDia
                )
            )

            db.collection("rutinas")
                .document(rutinaId)
                .set(rutinaData)
                .addOnSuccessListener {
                    Log.d(TAG, "Rutina creada con éxito.")

                    // Crear el mapa de días con los ejercicios
                    val diasMap = mutableMapOf<String, ArrayList<Exercise>>()
                    diasMap["lunes"] = ArrayList(exercises) // O el día que corresponda

                    // Crear la rutina con el nombre y el mapa de días
                    val rutina = Rutina(
                        nombre = nombreRutina, // Asegúrate de tener el nombre de la rutina
                        dias = diasMap // El mapa de días con los ejercicios
                    )

                    // Pasar la rutina al Intent
                    val intent = Intent()
                    intent.putExtra("rutina", rutina)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al guardar la rutina.", e)
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("exercises", exercises)
        super.onSaveInstanceState(outState)
    }
}

package com.example.helpgym

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helpgym.Adapter.ExerciseAdapter
import com.example.helpgym.Model.Exercise
import com.example.helpgym.databinding.ActivityEjerciciosBinding
import com.google.firebase.firestore.FirebaseFirestore

class EjerciciosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEjerciciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjerciciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        findViewById<View>(R.id.main)?.let { mainView ->
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } ?: Log.e("EjerciciosActivity", "View with ID 'main' not found")

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val exerciseList = ArrayList<Exercise>()
        val exerciseAdapter = ExerciseAdapter(exerciseList)
        recyclerView.adapter = exerciseAdapter

        fetchExercisesFromDatabase(exerciseList, exerciseAdapter)
    }

    private fun fetchExercisesFromDatabase(
        exerciseList: ArrayList<Exercise>,
        exerciseAdapter: ExerciseAdapter
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("ejercicios")
            .get()
            .addOnSuccessListener { documents ->
                val validDocuments = documents.filter { document ->
                    val sets = document.getString("series")
                    val reps = document.getString("repeticiones")
                    if (sets == null) {
                        Log.w(
                            "EjerciciosActivity",
                            "Field 'series' is missing or not a string: ${document.get("series")}"
                        )
                        false
                    } else if (reps == null) {
                        Log.w(
                            "EjerciciosActivity",
                            "Field 'repeticiones' is missing or not a string: ${document.get("repeticiones")}"
                        )
                        false
                    } else {
                        true
                    }
                }

                for (document in validDocuments) {
                    val name = document.getString("ejercicio") ?: ""
                    val sets = document.getString("series")!!
                    val reps = document.getString("repeticiones")!!
                    exerciseList.add(Exercise(name, sets, reps))
                }

                exerciseAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("EjerciciosActivity", "Error getting documents: ", exception)
            }
    }
}


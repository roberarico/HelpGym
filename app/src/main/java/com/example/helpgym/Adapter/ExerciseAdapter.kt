package com.example.helpgym.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helpgym.Model.Exercise
import com.example.helpgym.R

class ExerciseAdapter (private val exerciseList: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseName)
        val exerciseSets: TextView = itemView.findViewById(R.id.exerciseSets)
        val exerciseReps: TextView = itemView.findViewById(R.id.exerciseReps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ejercicios_adapter, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.exerciseName.text = exercise.name
        holder.exerciseSets.text = "Series: ${exercise.series}"
        holder.exerciseReps.text = "Repeticiones: ${exercise.repeticiones}"
    }

    override fun getItemCount() = exerciseList.size
}
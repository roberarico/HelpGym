package com.example.helpgym

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helpgym.databinding.ActivityMostrarBinding

class MostrarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMostrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMostrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val daysOfWeek = arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

        // Configurar el ListView con los días de la semana
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, daysOfWeek)
        binding.listViewDays.adapter = adapter
        binding.listViewDays.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@MostrarActivity, EjerciciosActivity::class.java)
            // Pasa el día seleccionado como extra en el intent
            intent.putExtra("day", daysOfWeek[position])
            startActivity(intent)
        }
    }
}
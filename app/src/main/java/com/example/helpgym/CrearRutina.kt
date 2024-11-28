package com.example.helpgym

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.helpgym.Model.Rutina
import com.example.helpgym.databinding.ActivityRutinaBinding
import com.google.firebase.firestore.FirebaseFirestore


class CrearRutina : AppCompatActivity() {
    private lateinit var binding: ActivityRutinaBinding
    private var listViewDays: ListView? = null
    private val daysOfWeek = arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listViewDays = binding.listViewDays
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daysOfWeek)
        listViewDays?.adapter = adapter

        listViewDays?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@CrearRutina, NuevaRutina::class.java)
            // Pasa el día seleccionado como extra en el intent
            intent.putExtra("day", daysOfWeek[position])
            startActivity(intent)
        }
        binding.guardarRutina.setOnClickListener{

        }
    }

    companion object {
        private const val REQUEST_CODE_ADD_ROUTINE = 1
    }

    // Método para recibir datos de vuelta de NuevaRutina
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Verifica si los datos de vuelta son para la solicitud de agregar rutina
        if (requestCode == REQUEST_CODE_ADD_ROUTINE && resultCode == Activity.RESULT_OK) {
            // Recibe la rutina de vuelta desde NuevaRutina
            val rutina = data?.getSerializableExtra("rutina") as? Rutina
            // Aquí puedes hacer lo que quieras con la rutina recibida de vuelta
        }
    }
}
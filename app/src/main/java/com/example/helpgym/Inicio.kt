package com.example.helpgym

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.helpgym.Model.Rutina
import com.example.helpgym.Model.Usuario
import com.example.helpgym.databinding.ActivityInicioBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Inicio : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var user: Usuario
    private var nombre: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInicioBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val email=intent.getStringExtra("correo").toString()
        recuperaUsuario(email)
        Log.v("ciclo","${recuperaUsuario(email)}")
        Log.v("ciclo", "$nombre")
        binding.usuario.text=nombre

        binding.crearRutina.setOnClickListener(this)
        binding.mostrarRutina.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.crearRutina.id -> {
                val intent = Intent(this, CrearRutina::class.java)
                startActivity(intent)

            }

            binding.mostrarRutina.id -> {
                val intent = Intent(this, MostrarActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun recuperaUsuario(email: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    //return fromFirestore(document)

                    Log.v("ciclo", "El usuario es: $user")
                    Log.v("ciclo", "El usuario es: $nombre")
                }
            }
            .addOnFailureListener { exception ->
                Log.v("ciclo", "Error getting documents: $exception")
            }
    }


    private fun fromFirestore(snapshot: DocumentSnapshot): Usuario {
        val data = snapshot.data ?: throw NullPointerException("DocumentSnapshot data is null")
        Log.v("ciclo", "Document data: $data")

        val nombre = data["name"] as? String ?: ""
        val usuario = data["username"] as? String ?: ""

        return Usuario(nombre, usuario)
    }
}
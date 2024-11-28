package com.example.helpgym

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.helpgym.Model.Usuario
import com.example.helpgym.databinding.ActivityRegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.registrar.setOnClickListener { v: View? ->
            // Obtenemos todos los campos
            val email = binding.correo.text.toString()
            val user = binding.usuario.text.toString()
            val name = binding.nombre.text.toString()
            val pass = binding.pass.text.toString()
            val repetirPass = binding.repetirPass.text.toString()

            // Si algún campo está vacío
            if (email.trim().isEmpty() || name.trim().isEmpty() || user.trim().isEmpty()
                || pass.trim().isEmpty() || repetirPass.trim().isEmpty()
            ) {
                Snackbar.make(
                    binding.registrar,
                    "Todos los campos deben estar llenos",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (pass != repetirPass) {
                Snackbar.make(
                    binding.registrar,
                    "Las contraseñas deben ser iguales",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (pass.length < 6) {
                Snackbar.make(
                    binding.registrar,
                    "Las contraseñas deben ser iguales y tener más de 6 caracteres",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                creaCuenta(email, pass,name, user)
                val usuario = Intent(this, Inicio::class.java)
                usuario.putExtra("usuario", Usuario(name, user, pass))
                startActivity(usuario)

            }
            binding.cancelar.setOnClickListener {
                val mainIntent = Intent(this, Welcome::class.java)
                startActivity(mainIntent)
            }


        }
    }

    private fun creaCuenta(email: String, password: String, name: String, username: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    // Guardar detalles del usuario en Cloud Firestore
                    val db = FirebaseFirestore.getInstance()
                    val user = hashMapOf(
                        "name" to name,
                        "username" to username,
                        "email" to email
                    )

                    db.collection("users").document(email)
                        .set(user)
                        .addOnSuccessListener {
                            // Redirigir al usuario a la pantalla de bienvenida
                            val mainIntent = Intent(this, Welcome::class.java)
                            startActivity(mainIntent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            // Manejar el error al guardar en Firestore
                            Log.e("Registro", "Error adding document", e)
                            Snackbar.make(
                                binding.registrar,
                                "Error al registrar el usuario",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    // Error en el registro
                    Log.e("Registro", "Error creating user", task.exception)
                    Snackbar.make(
                        binding.registrar,
                        "Error al registrar",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

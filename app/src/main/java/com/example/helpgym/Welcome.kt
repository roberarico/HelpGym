package com.example.helpgym

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.helpgym.databinding.ActivityWelcomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class Welcome : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding
    private val prefsName = "UserPrefs"
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

         prefs = getSharedPreferences(prefsName, MODE_PRIVATE)

        // Cargar los datos almacenados (si los hay)
        loadSavedCredentials()

        binding.botonRegistro.setOnClickListener(this)
        binding.botonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonRegistro.id -> {
                val intent = Intent(applicationContext, Registro::class.java)
                startActivity(intent)
            }
            binding.botonLogin.id -> {
                val mail = binding.correo.text.toString().trim()
                val pass = binding.pass.text.toString().trim()
                if (mail.isNotEmpty() && pass.isNotEmpty()) {
                    loginUser(mail, pass)
                    val intent=Intent(this,Inicio::class.java)
                    intent.putExtra("correo",mail)
                    startActivity(intent)
                } else {
                    Snackbar.make(binding.botonLogin, "Please fill in both email and password", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun loginUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login exitoso, navega a MainActivity
                    val intent = Intent(this, Inicio::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Error en el login
                    Snackbar.make(binding.botonLogin, "Error: ${task.exception?.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
    private fun loadSavedCredentials() {

        val savedEmail = prefs.getString("email", "")
        val savedPassword = prefs.getString("password", "")

        binding.correo.setText(savedEmail)
        binding.pass.setText(savedPassword)

        // Marcar el checkbox automáticamente si hay datos guardados
        binding.checkRecordar.isChecked = savedEmail?.isNotEmpty() == true && savedPassword?.isNotEmpty() == true
    }


}

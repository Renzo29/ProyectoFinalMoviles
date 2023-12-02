package com.example.agregar_editar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agregar_editar.entity.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var txtDni: TextInputLayout
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtPassword: TextInputLayout
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        auth = Firebase.auth
        txtDni = findViewById(R.id.textInputLayoutDNI)
        txtEmail = findViewById(R.id.textInputLayoutEmail)
        txtPassword = findViewById(R.id.textInputLayoutPassword)
    }

    fun signup(view: View) {
        val dni = txtDni.editText?.text.toString()
        val email = txtEmail.editText?.text.toString()
        val password = txtPassword.editText?.text.toString()

        // Crear un objeto Usuario
        val usuario = Usuario(dni, email, password)

        // Agregar el usuario en Firestore
        db.collection("usuarios")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                // Registro exitoso en Firestore
                Toast.makeText(this, "¡Usuario añadido!", Toast.LENGTH_SHORT).show()

                // Registrar al usuario en Firebase Authentication después del registro exitoso en Firestore
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso en Firebase Authentication
                            Toast.makeText(this, "$email registrado exitosamente", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            // Fallo al registrar en Firebase Authentication
                            Toast.makeText(this, "Fallo al registrar en Firebase Authentication", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            .addOnFailureListener { e ->
                // Fallo al agregar usuario en Firestore
                Toast.makeText(this, "¡Ocurrió un error al añadir el usuario en Firestore!", Toast.LENGTH_SHORT).show()
                println("Error al agregar documento: $e")
            }
    }
}

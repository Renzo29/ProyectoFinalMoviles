package com.example.agregar_editar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agregar_editar.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class RecoveryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var txtDni: TextInputLayout
    private lateinit var txtEmail: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recovery_layout)

        auth = Firebase.auth
        txtDni = findViewById(R.id.textInputLayoutDni)
        txtEmail = findViewById(R.id.textInputLayoutEmail)
    }

    fun recoverPassword(view: View) {
        val dni = txtDni.editText?.text.toString()
        val email = txtEmail.editText?.text.toString()

        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {

                    val storedDni = querySnapshot.documents[0].getString("dni")
                    if (storedDni == dni) {
                        sendPasswordResetEmail(email)
                        Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Información incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "Error al buscar usuario: $e", Toast.LENGTH_SHORT).show()
            }
    }


    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {

                }
            }
    }
}

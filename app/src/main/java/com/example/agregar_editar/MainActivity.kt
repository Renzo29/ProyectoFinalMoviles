package com.example.agregar_editar

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtPassword: TextInputLayout
    private lateinit var email: String
    private lateinit var password: String

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, ListadoActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        auth = Firebase.auth
        txtEmail = findViewById(R.id.textInputLayoutEmail)
        txtPassword = findViewById(R.id.textInputLayoutPassword)

        val forgotPasswordTextView: TextView = findViewById(R.id.textView9)
        val signUpTextView: TextView = findViewById(R.id.textView10)

        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(view: View) {
        if (!validar())
            return

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    startActivity(Intent(this, ListadoActivity::class.java))
                    finish()
                } else {

                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    txtPassword.editText?.setText("")
                }
            }
    }

    fun validar(): Boolean {
        email = txtEmail.editText?.text.toString()
        if (TextUtils.isEmpty(email)) {
            txtEmail.error = "Ingresar correo"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.error = "Ingresar en formato de correo"
            return false
        } else {
            txtEmail.error = null
        }

        password = txtPassword.editText?.text.toString()

        if (TextUtils.isEmpty(password)) {
            txtPassword.error = "Ingresar contraseña"
            return false
        } else {
            txtPassword.error = null
        }
        return true
    }
}

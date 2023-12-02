package com.example.agregar_editar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Hamburguesa_list
import com.google.firebase.firestore.FirebaseFirestore

class EditarActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var txtNombre: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtCodigo: EditText
    private lateinit var txtPreparacion: EditText
    private lateinit var radioPan: RadioGroup
    private lateinit var hamburguesaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_layout)

        txtCodigo = findViewById(R.id.txt_codigo)
        txtNombre = findViewById(R.id.txt_nomhamburguesa)
        txtDescripcion = findViewById(R.id.txt_deschamburguesa)
        txtPreparacion = findViewById(R.id.txt_preparacion)
        radioPan = findViewById(R.id.rd_tipo_pan)

        obtenerDatosHamburguesa()

        val btnGuardar = findViewById<Button>(R.id.btn_agregar)
        btnGuardar.setOnClickListener {
            actualizarHamburguesa()
        }
    }

    private fun obtenerDatosHamburguesa() {
        val db = FirebaseFirestore.getInstance()
        val nombre = intent.getStringExtra("nombre")

        db.collection("Hamburguesa_lista")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.exists()) {
                        val hamburguesa = document.toObject(Hamburguesa_list::class.java)
                        mostrarDatosHamburguesa(hamburguesa)
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al obtener datos: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun mostrarDatosHamburguesa(hamburguesa: Hamburguesa_list?) {
        if (hamburguesa != null) {
            txtCodigo.setText(hamburguesa.codigo)
            txtNombre.setText(hamburguesa.nombre)
            txtDescripcion.setText(hamburguesa.descripcion)
            txtPreparacion.setText(hamburguesa.preparacion)

            val tipoPan = hamburguesa.tipoPan

            val radioId = when (tipoPan) {
                "Pan Normal" -> R.id.radio_pan_normal
                "Pan Integral" -> R.id.radio_pan_integral
                else -> R.id.radio_pan_bollo
            }

            val radioButton: RadioButton = findViewById(radioId)
            radioPan.check(radioButton.id)
        } else {
            Toast.makeText(this, "La hamburguesa no existe", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarHamburguesa() {
        val codigo = txtCodigo.text.toString()
        val nombre = txtNombre.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val preparacion = txtPreparacion.text.toString()
        val tipoPan: String

        val radioButtonSeleccionadoId = radioPan.checkedRadioButtonId
        val radioButtonSeleccionado: RadioButton = findViewById(radioButtonSeleccionadoId)
        tipoPan = radioButtonSeleccionado.text.toString()

        val nuevaHamburguesa = Hamburguesa_list(codigo, nombre, descripcion, tipoPan)

        db.collection("Hamburguesa_lista").document(hamburguesaId)
            .set(nuevaHamburguesa)
            .addOnSuccessListener {
                Toast.makeText(this, "Hamburguesa actualizada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al actualizar: $e", Toast.LENGTH_SHORT).show()
            }
    }
}

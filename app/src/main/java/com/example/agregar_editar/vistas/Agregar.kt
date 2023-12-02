package com.example.agregar_editar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Hamburguesa_list
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Agregar : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    lateinit var txtNombre: EditText
    lateinit var txtDescripcion: EditText
    lateinit var txtCodigo: EditText
    lateinit var txtPreparacion: EditText
    lateinit var txtImagen: EditText
    lateinit var radioPan: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar2)

        txtCodigo = findViewById(R.id.txt_codigo)
        txtNombre = findViewById(R.id.txt_nomhamburguesa)
        txtImagen = findViewById(R.id.txt_img)
        txtDescripcion = findViewById(R.id.txt_deschamburguesa)
        txtPreparacion = findViewById(R.id.txt_preparacion)
        radioPan = findViewById(R.id.rd_tipo_pan)
    }

    fun agregarHamburguesa(view: View) {

        if (!camposSonValidos()) {
            return
        }


        val hamburguesa = Hamburguesa_list(
            txtCodigo.text.toString().trim(),
            txtNombre.text.toString().trim(),
            txtDescripcion.text.toString().trim(),
            txtImagen.text.toString().trim(),
            obtenerTipoPan(),
            txtPreparacion.text.toString().trim()
        )

        db.collection("Hamburguesa_lista")
            .add(hamburguesa)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "¡Hamburguesa añadida!", Toast.LENGTH_SHORT).show()
                println("Documento agregado con ID: ${documentReference.id}")
                limpiarCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "¡Ocurrió un error al añadir!", Toast.LENGTH_SHORT).show()
                println("Error al agregar documento: $e")
            }
    }


    private fun camposSonValidos(): Boolean {
        val imagen = txtImagen.text.toString().trim()
        val codigo = txtCodigo.text.toString().trim()
        val nombre = txtNombre.text.toString().trim()
        val descripcion = txtDescripcion.text.toString().trim()
        val preparacion = txtPreparacion.text.toString().trim()

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || preparacion.isEmpty() || imagen.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        val radioButtonSeleccionadoId = radioPan.checkedRadioButtonId
        if (radioButtonSeleccionadoId == -1) {
            Toast.makeText(this, "Por favor, seleccione el tipo de pan", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    private fun limpiarCampos() {
        txtCodigo.text.clear()
        txtNombre.text.clear()
        txtImagen.text.clear()
        txtDescripcion.text.clear()
        txtPreparacion.text.clear()
        radioPan.clearCheck()
    }


    private fun obtenerTipoPan(): String {
        val radioButtonSeleccionadoId = radioPan.checkedRadioButtonId
        val radioButtonSeleccionado: RadioButton = findViewById(radioButtonSeleccionadoId)
        return radioButtonSeleccionado.text.toString()
    }



    fun cancelar(view: View){
        startActivity(Intent(this,ListadoActivity::class.java))
    }

}
package com.example.agregar_editar.vistas

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

        val imagen = txtImagen.text.toString()
        val codigo = txtCodigo.text.toString()
        val nombre = txtNombre.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val preparacion = txtPreparacion.toString()
        val tipoPan: String

        val radioButtonSeleccionadoId = radioPan.checkedRadioButtonId

        val radioButtonSeleccionado: RadioButton = findViewById(radioButtonSeleccionadoId)
        tipoPan = radioButtonSeleccionado.text.toString()

        val hamburguesa = Hamburguesa_list(codigo, nombre, descripcion, imagen, tipoPan)

        db.collection("Hamburguesa_lista")
            .add(hamburguesa)
            .addOnSuccessListener { documentReference ->

                Toast.makeText(this, "¡Hamburguesa añadida!", Toast.LENGTH_SHORT).show()
                println("Documento agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "¡Ocurrió un error al añadir!", Toast.LENGTH_SHORT).show()
                println("Error al agregar documento: $e")
            }
    }
}
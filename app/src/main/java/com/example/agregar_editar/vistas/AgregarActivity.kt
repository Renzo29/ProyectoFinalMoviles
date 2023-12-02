package com.example.agregar_editar.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.adapter.IngredienteAdapter
import com.example.agregar_editar.entity.Detalle_Hamburguesa
import com.example.agregar_editar.entity.Hamburguesa_list
import com.example.agregar_editar.entity.Ingrediente
import com.example.agregar_editar.entity.IngredienteCheckBox
import com.google.firebase.firestore.FirebaseFirestore

class AgregarActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    lateinit var txtNombre: EditText
    lateinit var txtDescripcion: EditText
    lateinit var txtCodigo: EditText
    lateinit var txtPreparacion: EditText
    lateinit var txtImagen: EditText
    lateinit var radioPan: RadioGroup
    val ingredientesList = mutableListOf<IngredienteCheckBox>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar2)

        txtCodigo = findViewById(R.id.txt_codigo)
        txtNombre = findViewById(R.id.txt_nomhamburguesa)
        txtImagen = findViewById(R.id.txt_img)
        txtDescripcion = findViewById(R.id.txt_deschamburguesa)
        txtPreparacion = findViewById(R.id.txt_preparacion)
        radioPan = findViewById(R.id.rd_tipo_pan)

        db.collection("Ingredientes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val ingrediente = document.toObject(Ingrediente::class.java)
                    ingredientesList.add(IngredienteCheckBox(ingrediente))
                }

                val recyclerView: RecyclerView = findViewById(R.id.recycler_ingredientes)
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager

                val adapter = IngredienteAdapter(ingredientesList) { ingrediente ->
                }
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                println(exception)
            }
    }
    fun agregarHamburguesa(view: View) {

        val imagen = txtImagen.text.toString()
        val codigo = txtCodigo.text.toString()
        val nombre = txtNombre.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val preparacion = txtPreparacion.text.toString()
        val tipoPan: String

        val radioButtonSeleccionadoId = radioPan.checkedRadioButtonId

        val radioButtonSeleccionado: RadioButton = findViewById(radioButtonSeleccionadoId)
        tipoPan = radioButtonSeleccionado.text.toString()

        val hamburguesa = Hamburguesa_list(codigo, nombre, descripcion, imagen, tipoPan, preparacion)

        db.collection("Hamburguesa_lista")
            .add(hamburguesa)
            .addOnSuccessListener { documentReference ->

                Toast.makeText(this, "¡Hamburguesa añadida!", Toast.LENGTH_SHORT).show()
                println("Documento agregado con ID: ${documentReference.id}")

                val idHamburguesa = documentReference.id

                val ingredientesSeleccionados = ingredientesList.filter { it.isSelected }


                for (ingrediente in ingredientesSeleccionados) {
                    agregarDetalleHamburguesa(idHamburguesa, ingrediente.ingrediente)
                }

            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "¡Ocurrió un error al añadir!", Toast.LENGTH_SHORT).show()
                println("Error al agregar documento: $e")
            }
    }

    private fun agregarDetalleHamburguesa(idHamburguesa: String, ingrediente: Ingrediente) {

        val codigoIngrediente = ingrediente.codigo
        val cantidad = 2

        val detalleHamburguesa = Detalle_Hamburguesa(idHamburguesa, codigoIngrediente, cantidad)

        db.collection("Detalle_Hamburguesa")
            .add(detalleHamburguesa)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "¡Hamburguesa y detalle añadidos!", Toast.LENGTH_SHORT).show()
                println("Documento de detalle de hamburguesa agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "¡Ocurrió un error al añadir el detalle de la hamburguesa!", Toast.LENGTH_SHORT).show()
                println("Error al agregar el detalle de hamburguesa: $e")
            }
    }
}
package com.example.agregar_editar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.adapter.HamburguesaAdapter
import com.example.agregar_editar.entity.Hamburguesa_list

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListadoActivity : AppCompatActivity() {

    lateinit var adapter: HamburguesaAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var txtEncontrados : TextView
    lateinit var txtBuscar : TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)
        txtEncontrados = findViewById(R.id.txt_resultados)
        recyclerView = findViewById(R.id.recycler_list)
        txtBuscar = findViewById(R.id.txt_buscar)
        /*val db = Firebase.firestore

        val lista = mutableListOf<Hamburguesa_list>()
        db.collection("Hamburguesa_lista").get()
            .addOnSuccessListener{resultado ->
                for (documento in resultado){
                    val cantidad = resultado.size()
                    lista.add(documento.toObject(Hamburguesa_list::class.java))
                    adapter = HamburguesaAdapter(lista)
                    recyclerView.adapter=adapter
                    txtEncontrados.text ="Cantidad de resultados encontrados: $cantidad"
                }
            }.addOnFailureListener{
                Toast.makeText(baseContext,"Error al cargar", Toast.LENGTH_SHORT).show()
            }
        recyclerView.layoutManager = LinearLayoutManager(this)*/
    }
    fun listar(terminoBusqueda: String? = null) {
        val db = Firebase.firestore
        val lista = mutableListOf<Hamburguesa_list>()

        db.collection("Hamburguesa_lista").get()
            .addOnSuccessListener { resultado ->
                for (documento in resultado) {
                    lista.add(documento.toObject(Hamburguesa_list::class.java))
                }

                val adapter = HamburguesaAdapter(lista)
                recyclerView.adapter = adapter
                adapter.filter.filter(terminoBusqueda)

                val cantidad = adapter.listaFiltrada.size

                txtEncontrados.text = " $cantidad Hamburguesas encontradas"
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Error al cargar", Toast.LENGTH_SHORT).show()
            }

        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    fun buscarHamburguesa(view: View) {
        val terminoBusqueda = txtBuscar.editText?.text?.toString()
        Toast.makeText(this, terminoBusqueda, Toast.LENGTH_SHORT).show()

        listar(terminoBusqueda)
    }




    fun editar(view: View) {
        // Obtén el nombre de la hamburguesa
        val nombreHamburguesa = obtenerNombreHamburguesaDesdeVista(view)

        // Imprime el nombre en la consola
        Log.d("ListadoActivity", "Nombre de la hamburguesa: $nombreHamburguesa")

        // Luego, abre la actividad de edición
        abrirActividadEdicion(nombreHamburguesa)
    }

    private fun obtenerNombreHamburguesaDesdeVista(view: View): String {
        try {
            val txtNombre = view.findViewById<TextView>(R.id.txt_nombre)
            return txtNombre.text.toString()
        } catch (e: Exception) {
            Log.e("ListadoActivity", "Error al obtener el nombre de la hamburguesa: $e")
            return "Error"
        }
    }

    private fun abrirActividadEdicion(nombreHamburguesa: String) {

        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("nombre", nombreHamburguesa)
        startActivity(intent)
    }
    fun add(view: View){
        startActivity(Intent(this,AgregarActivity::class.java))
    }

    fun logout(view: View){
        com.google.firebase.Firebase.auth.signOut()
        startActivity(Intent(this,MainActivity::class.java))
    }

}
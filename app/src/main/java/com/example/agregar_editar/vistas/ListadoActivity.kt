package com.example.agregar_editar.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.adapter.HamburguesaAdapter
import com.example.agregar_editar.entity.Hamburguesa_list
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListadoActivity : AppCompatActivity() {

    lateinit var adapter: HamburguesaAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var txtEncontrados : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)
        txtEncontrados = findViewById(R.id.txt_resultado)
        recyclerView = findViewById(R.id.recycler_lista)
        val db = Firebase.firestore

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
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
package com.example.agregar_editar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Hamburguesa_list
import com.squareup.picasso.Picasso

class HamburguesaAdapter (private val dataset: MutableList<Hamburguesa_list>):
    RecyclerView.Adapter<HamburguesaAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
          val txtDescripcion : TextView
        val imagen : ImageView
        val txtNombre : TextView
        init {
            txtNombre =itemView.findViewById(R.id.txt_nombre)
            txtDescripcion = itemView.findViewById(R.id.txt_descripcion)
            imagen = itemView.findViewById(R.id.imagen)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from (parent.context).inflate(R.layout.listado_item, parent, false)
        return HamburguesaAdapter.ViewHolder(view)
    }

    override fun getItemCount()= dataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receta = dataset[position]

        holder.txtNombre.text = receta.nombre
        holder.txtDescripcion.text = receta.descripcion
        Picasso.get().load(receta.imagen).into(holder.imagen)
    }
}
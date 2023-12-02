package com.example.agregar_editar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Ingrediente
import com.example.agregar_editar.entity.IngredienteCheckBox

class IngredienteAdapter(
    private val ingredientes: List<IngredienteCheckBox>,
    private val onIngredientCheckedListener: (IngredienteCheckBox) -> Unit
) : RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkIngrediente) // cambiado

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingrediente_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder.checkBox.text = ingrediente.ingrediente.nombre
        holder.checkBox.isChecked = ingrediente.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            ingrediente.isSelected = isChecked
            onIngredientCheckedListener(ingrediente)
        }
    }

    override fun getItemCount(): Int {
        return ingredientes.size
    }
}
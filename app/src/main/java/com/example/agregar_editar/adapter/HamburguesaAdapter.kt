package com.example.agregar_editar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.EditarActivity
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Hamburguesa_list
import com.squareup.picasso.Picasso

class HamburguesaAdapter(private var dataset: MutableList<Hamburguesa_list>) :
    RecyclerView.Adapter<HamburguesaAdapter.ViewHolder>(), Filterable {

    public var listaFiltrada: List<Hamburguesa_list> = dataset

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txt_nombre)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txt_descripcion)
        val imagen: ImageView = itemView.findViewById(R.id.imagen)
        val imgEditar: ImageView = itemView.findViewById(R.id.img_editar)

        init {
            imgEditar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val hamburguesa = listaFiltrada[position]
                    // Pasa el código de la hamburguesa a la actividad de edición
                    // Asegúrate de haber definido tu actividad de Editar en el AndroidManifest.xml
                    val intent = Intent(itemView.context, EditarActivity::class.java)
                    intent.putExtra("codigo", hamburguesa.codigo)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listado_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listaFiltrada.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hamburguesa = listaFiltrada[position]

        holder.txtNombre.text = hamburguesa.nombre
        holder.txtDescripcion.text = hamburguesa.descripcion
        Picasso.get().load(hamburguesa.imagen).into(holder.imagen)
    }

    // Método para realizar la filtración
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filtrarResultados = FilterResults()
                val filtrados = mutableListOf<Hamburguesa_list>()

                if (charSequence.isEmpty()) {
                    filtrados.addAll(dataset)
                } else {
                    val filtro = charSequence.toString().toLowerCase().trim()
                    for (hamburguesa in dataset) {
                        if (hamburguesa.nombre.toLowerCase().contains(filtro)) {
                            filtrados.add(hamburguesa)
                        }
                    }
                }

                filtrarResultados.values = filtrados
                return filtrarResultados
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                listaFiltrada = results?.values as? List<Hamburguesa_list> ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }
}
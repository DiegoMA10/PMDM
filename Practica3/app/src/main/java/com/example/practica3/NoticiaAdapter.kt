package com.example.practica3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.practica3.databinding.ItemNoticiaBinding

class NoticiaAdapter(private var noticias: List<Noticia>, private val listener: OnClickListener) : RecyclerView.Adapter<NoticiaAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNoticiaBinding.bind(view)

        fun setListener(new: Noticia, position: Int) {
            binding.root.setOnClickListener { listener.onClick(new, position) }
        }
    }

    fun updateData(newNoticias: List<Noticia>) {
        noticias = newNoticias
        notifyDataSetChanged()  // Notifica que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = noticias.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = noticias[position]
        with(holder) {
            setListener(new, position + 1)
            binding.tvTitle.text = new.titulo
            binding.tvDescription.text = new.resumen
            binding.tvDate.text = new.fecha
            Glide.with(binding.tvImage.context)
                .load(new.imagenUrl)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.tvImage)
        }
    }
}
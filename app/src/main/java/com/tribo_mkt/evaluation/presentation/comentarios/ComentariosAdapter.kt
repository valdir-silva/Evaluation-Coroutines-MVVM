package com.tribo_mkt.evaluation.presentation.comentarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.model.Comentario
import com.tribo_mkt.evaluation.data.model.Postagem
import com.tribo_mkt.evaluation.data.model.Usuario
import kotlinx.android.synthetic.main.comment_view.view.*
import kotlinx.android.synthetic.main.comment_view.view.titulo
import kotlinx.android.synthetic.main.post_view.view.*

class ComentariosAdapter(private var comentarios: ArrayList<Comentario>) :
    RecyclerView.Adapter<ComentariosAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comentario: Comentario) {
            itemView.apply {
                titulo.text = comentario.name
                comentarioTextView.text = comentario.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_view, parent, false)
        )

    override fun getItemCount(): Int = comentarios.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    fun addComentarios(comentarios: List<Comentario>) {
        this.comentarios.apply {
            clear()
            addAll(comentarios)
        }
    }
}
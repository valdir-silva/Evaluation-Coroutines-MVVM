package com.tribo_mkt.evaluation.presentation.fotos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.model.Foto
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.fotoDetalhe.FotoDetalheActivity
import kotlinx.android.synthetic.main.photo_view.view.*

class FotosAdapter(private var fotos: ArrayList<Foto>, _usuario: Usuario) :
    RecyclerView.Adapter<FotosAdapter.DataViewHolder>() {

    var usuario = _usuario

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(foto: Foto, usuario: Usuario) {
            itemView.apply {
                Picasso.get().load(foto.thumbnailUrl).into(thumb)
                titulo.text = foto.title

                itemView.setOnClickListener {
                    FotoDetalheActivity.getStatIntent(
                        itemView.context,
                        foto.albumId,
                        usuario.id,
                        usuario.name,
                        foto.url,
                        foto.title
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_view, parent, false)
        )

    override fun getItemCount(): Int = fotos.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(fotos[position], usuario)
    }

    fun addFotos(fotos: List<Foto>) {
        this.fotos.apply {
            clear()
            addAll(fotos)
        }
    }
}

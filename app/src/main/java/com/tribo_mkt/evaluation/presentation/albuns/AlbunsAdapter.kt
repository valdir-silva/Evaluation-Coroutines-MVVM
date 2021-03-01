package com.tribo_mkt.evaluation.presentation.albuns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.model.Album
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.fotos.FotosActivity
import kotlinx.android.synthetic.main.album_view.view.*
import kotlinx.android.synthetic.main.usuario_view.view.*

class AlbunsAdapter(private var albuns: ArrayList<Album>, _usuario: Usuario) :
    RecyclerView.Adapter<AlbunsAdapter.DataViewHolder>() {

    var usuario = _usuario

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album, usuario: Usuario) {
            itemView.apply {
                albumTextView.text = album.title

                itemView.setOnClickListener {
                    FotosActivity.getStatIntent(
                        itemView.context,
                        album.id,
                        usuario.id,
                        usuario.name
                    )
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.album_view, parent, false)
        )

    override fun getItemCount(): Int = albuns.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(albuns[position], usuario)
    }

    fun addAlbuns(albuns: List<Album>) {
        this.albuns.apply {
            clear()
            addAll(albuns)
        }
    }
}
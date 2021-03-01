package com.tribo_mkt.evaluation.presentation.inicio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.albuns.AlbunsActivity
import com.tribo_mkt.evaluation.presentation.postagens.PostagensActivity
import kotlinx.android.synthetic.main.usuario_view.view.*

class InicioAdapter(private var users: ArrayList<Usuario>) :
    RecyclerView.Adapter<InicioAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(usuario: Usuario) {
            itemView.apply {
                usuarioNome.text = usuario.username
                email.text = usuario.email
                telefone.text = usuario.phone
                nome.text = usuario.name
                letra.text = usuario.name.substring(0, 2).toUpperCase()

                if ((adapterPosition - 1) % 2 == 0) {
                    fundo.setBackgroundResource(R.color.fundo)
                }
                itemView.albunsBotao.setOnClickListener {
                    AlbunsActivity.getStatIntent(
                        itemView.context,
                        usuario.id,
                        usuario.name
                    )
                }
                itemView.postagensBotao.setOnClickListener {
                    PostagensActivity.getStatIntent(
                        itemView.context,
                        usuario.id,
                        usuario.name
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.usuario_view, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUsers(users: List<Usuario>) {
        this.users.apply {
            clear()
            addAll(users)
        }
    }
}
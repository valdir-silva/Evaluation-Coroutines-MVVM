package com.tribo_mkt.evaluation.presentation.postagens

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.model.Postagem
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.comentarios.ComentariosActivity
import com.tribo_mkt.evaluation.presentation.comentarios.ComentariosViewModel
import com.tribo_mkt.evaluation.utils.Status
import kotlinx.android.synthetic.main.activity_comentarios.*
import kotlinx.android.synthetic.main.post_view.view.*

class PostagensAdapter(private var postagens: ArrayList<Postagem>, _usuario: Usuario, _viewModel: ComentariosViewModel, _lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<PostagensAdapter.DataViewHolder>() {

    var usuario = _usuario
    var viewModel = _viewModel
    var lifecycleOwner = _lifecycleOwner

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(postagem: Postagem, usuario: Usuario, viewModel: ComentariosViewModel, lifecycleOwner: LifecycleOwner) {
            itemView.apply {
                titulo.text = postagem.title

                viewModel.getComentarios(postagem.id).observe(lifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { comentarios ->
                                    val text: String = resources.getString(R.string.numero_comentarios)
                                    comentariosTextView.setText(text.plus(" " + comentarios.size))
                                }
                            }
                            Status.ERROR -> {
                                Toast.makeText(itemView.context, R.string.error, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })

                itemView.setOnClickListener {
                    ComentariosActivity.getStatIntent(
                            itemView.context,
                            postagem.id,
                            usuario.id,
                            usuario.name
                        )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false)
        )

    override fun getItemCount(): Int = postagens.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(postagens[position], usuario, viewModel, lifecycleOwner)
    }

    fun addPostagens(postagens: List<Postagem>) {
        this.postagens.apply {
            clear()
            addAll(postagens)
        }
    }
}
package com.tribo_mkt.evaluation.presentation.comentarios

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.api.RetrofitBuilder
import com.tribo_mkt.evaluation.data.model.Comentario
import com.tribo_mkt.evaluation.data.model.Postagem
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import com.tribo_mkt.evaluation.presentation.postagens.PostagensActivity
import com.tribo_mkt.evaluation.utils.Status
import kotlinx.android.synthetic.main.activity_comentarios.*
import kotlinx.android.synthetic.main.activity_postagens.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ComentariosActivity : BaseActivity() {

    private lateinit var viewModel: ComentariosViewModel
    private lateinit var adapter: ComentariosAdapter

    var usuario: Usuario = Usuario("", "", "", "", "")
    var postagem: Postagem = Postagem("", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        setupExtras()
        setupToolbar(toolBarMain, getString(R.string.comentarios_title, usuario.name), true)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupExtras() {
        usuario.id = intent.getStringExtra(EXTRA_USER_ID)
        usuario.name = intent.getStringExtra(EXTRA_USER_NOME)

        postagem.id = intent.getStringExtra(EXTRA_POST_ID)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ComentariosViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(ComentariosViewModel::class.java)
    }

    private fun setupUI() {
        recycleComentarios.layoutManager = LinearLayoutManager(this)
        adapter = ComentariosAdapter(arrayListOf())
        recycleComentarios.addItemDecoration(
            DividerItemDecoration(
                recycleComentarios.context,
                (recycleComentarios.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleComentarios.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getComentarios(postagem.id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recycleComentarios.visibility = View.VISIBLE
                        progressBarComentarios.visibility = View.GONE
                        resource.data?.let { comentarios -> retrieveList(comentarios) }
                    }
                    Status.ERROR -> {
                        recycleComentarios.visibility = View.VISIBLE
                        progressBarComentarios.visibility = View.GONE
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarComentarios.visibility = View.VISIBLE
                        recycleComentarios.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(comentarios: List<Comentario>) {
        adapter.apply {
            addComentarios(comentarios)
            notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        PostagensActivity.getStatIntent(
            this@ComentariosActivity,
            intent.getStringExtra(ComentariosActivity.EXTRA_USER_ID),
            intent.getStringExtra(ComentariosActivity.EXTRA_USER_NOME)
        )
        return true
    }

    companion object {
        private const val EXTRA_POST_ID = "EXTRA_POST_ID"
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_USER_NOME = "EXTRA_USER_NOME"

        fun getStatIntent(
            contex: Context,
            postId: String,
            usuarioId: String,
            usuarioNome: String
        ) {
            val intent = Intent(contex, ComentariosActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, postId)
                putExtra(EXTRA_USER_ID, usuarioId)
                putExtra(EXTRA_USER_NOME, usuarioNome)
            }
            contex.startActivity(intent)
        }
    }
}
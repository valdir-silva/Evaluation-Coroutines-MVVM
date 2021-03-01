package com.tribo_mkt.evaluation.presentation.postagens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.api.RetrofitBuilder
import com.tribo_mkt.evaluation.data.model.Postagem
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import com.tribo_mkt.evaluation.presentation.comentarios.ComentariosViewModel
import com.tribo_mkt.evaluation.utils.Status
import kotlinx.android.synthetic.main.activity_postagens.*
import kotlinx.android.synthetic.main.include_toolbar.*

class PostagensActivity : BaseActivity() {

    private lateinit var viewModelComentarios: ComentariosViewModel
    private lateinit var viewModel: PostagensViewModel
    private lateinit var adapter: PostagensAdapter

    var usuario: Usuario = Usuario("", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postagens)

        setupExtras()
        setupToolbar(
            toolBarMain, getString(
                R.string.postagens_title, usuario.name
            ), true
        )

        setupViewModel()
        setupUI()
        setupObservers()

    }

    private fun setupExtras() {
        usuario.id = intent.getStringExtra(EXTRA_USER_ID)
        usuario.name = intent.getStringExtra(EXTRA_USER_NOME)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            PostagensViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(PostagensViewModel::class.java)

        viewModelComentarios = ViewModelProviders.of(
            this,
            ComentariosViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(ComentariosViewModel::class.java)
    }

    private fun setupUI() {
        recyclePostagens.layoutManager = LinearLayoutManager(this)
        adapter = PostagensAdapter(arrayListOf(), usuario, viewModelComentarios, this@PostagensActivity)
        recyclePostagens.addItemDecoration(
            DividerItemDecoration(
                recyclePostagens.context,
                (recyclePostagens.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclePostagens.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getPostagens(usuario.id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclePostagens.visibility = View.VISIBLE
                        progressBarPostagens.visibility = View.GONE
                        resource.data?.let { postagens ->
                            retrieveList(postagens)
                        }
                    }
                    Status.ERROR -> {
                        recyclePostagens.visibility = View.VISIBLE
                        progressBarPostagens.visibility = View.GONE
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarPostagens.visibility = View.VISIBLE
                        recyclePostagens.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(postagens: List<Postagem>) {
        adapter.apply {
            addPostagens(postagens)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_USER_NOME = "EXTRA_USER_NOME"

        fun getStatIntent(
            contex: Context,
            id: String,
            nome: String
        ) {
            val intent = Intent(contex, PostagensActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, id)
                putExtra(EXTRA_USER_NOME, nome)
            }
            contex.startActivity(intent)
        }
    }
}
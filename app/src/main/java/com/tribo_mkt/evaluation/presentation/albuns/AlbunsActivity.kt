package com.tribo_mkt.evaluation.presentation.albuns

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
import com.tribo_mkt.evaluation.data.model.Album
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import com.tribo_mkt.evaluation.utils.Status
import kotlinx.android.synthetic.main.activity_albuns.*
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.include_toolbar.*

class AlbunsActivity : BaseActivity() {

    private lateinit var viewModel: AlbunsViewModel
    private lateinit var adapter: AlbunsAdapter

    var usuario: Usuario = Usuario("", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albuns)

        setupExtras()
        setupToolbar(toolBarMain, getString(R.string.albuns_title, usuario.name), true)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupExtras(){
        usuario.id = intent.getStringExtra(EXTRA_USER_ID)
        usuario.name = intent.getStringExtra(EXTRA_USER_NOME)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            AlbunsViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(AlbunsViewModel::class.java)
    }

    private fun setupUI() {
        recycleAlbuns.layoutManager = LinearLayoutManager(this)
        adapter = AlbunsAdapter(arrayListOf(), usuario)
        recycleAlbuns.addItemDecoration(
            DividerItemDecoration(
                recycleAlbuns.context,
                (recycleAlbuns.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleAlbuns.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getAlbuns(usuario.id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recycleAlbuns.visibility = View.VISIBLE
                        progressBarAlbuns.visibility = View.GONE
                        resource.data?.let { albuns -> retrieveList(albuns) }
                    }
                    Status.ERROR -> {
                        recycleAlbuns.visibility = View.VISIBLE
                        progressBarAlbuns.visibility = View.GONE
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarAlbuns.visibility = View.VISIBLE
                        recycleAlbuns.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(albuns: List<Album>) {
        adapter.apply {
            addAlbuns(albuns)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_USER_NOME = "EXTRA_USER_NOME"

        fun getStatIntent(
            contex: Context,
            usuarioId: String,
            usuarioNome: String
        ) {
            val intent = Intent(contex, AlbunsActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, usuarioId)
                putExtra(EXTRA_USER_NOME, usuarioNome)
            }
            contex.startActivity(intent)
        }
    }
}

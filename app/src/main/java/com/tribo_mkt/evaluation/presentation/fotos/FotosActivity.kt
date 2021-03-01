package com.tribo_mkt.evaluation.presentation.fotos

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
import com.tribo_mkt.evaluation.data.model.Album
import com.tribo_mkt.evaluation.data.model.Foto
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.albuns.AlbunsActivity
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import com.tribo_mkt.evaluation.utils.Status
import kotlinx.android.synthetic.main.activity_albuns.*
import kotlinx.android.synthetic.main.activity_fotos.*
import kotlinx.android.synthetic.main.include_toolbar.*

class FotosActivity : BaseActivity() {

    private lateinit var viewModel: FotosViewModel
    private lateinit var adapter: FotosAdapter

    var usuario: Usuario = Usuario("", "", "", "", "")
    var album: Album = Album("", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)

        setupExtras()
        setupToolbar(toolBarMain, getString(R.string.fotos_title, usuario.name), true)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupExtras() {
        usuario.id = intent.getStringExtra(EXTRA_USER_ID)
        usuario.name = intent.getStringExtra(EXTRA_USER_NOME)

        album.id = intent.getStringExtra(EXTRA_ALBUM_ID)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            FotosViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(FotosViewModel::class.java)
    }

    private fun setupUI() {
        recycleFotos.layoutManager = LinearLayoutManager(this)
        adapter = FotosAdapter(arrayListOf(), usuario)
        recycleFotos.addItemDecoration(
            DividerItemDecoration(
                recycleFotos.context,
                (recycleFotos.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleFotos.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getFotos(album.id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recycleFotos.visibility = View.VISIBLE
                        progressBarFotos.visibility = View.GONE
                        resource.data?.let { fotos -> retrieveList(fotos) }
                    }
                    Status.ERROR -> {
                        recycleFotos.visibility = View.VISIBLE
                        progressBarFotos.visibility = View.GONE
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarFotos.visibility = View.VISIBLE
                        recycleFotos.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(fotos: List<Foto>) {
        adapter.apply {
            addFotos(fotos)
            notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlbunsActivity.getStatIntent(
            this@FotosActivity,
            intent.getStringExtra(EXTRA_USER_ID),
            intent.getStringExtra(EXTRA_USER_NOME)
        )
        return true
    }

    companion object {
        private const val EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID"
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_USER_NOME = "EXTRA_USER_NOME"

        fun getStatIntent(
            contex: Context,
            albumId: String,
            usuarioId: String,
            usuarioNome: String
        ) {
            val intent = Intent(contex, FotosActivity::class.java).apply {
                putExtra(EXTRA_ALBUM_ID, albumId)
                putExtra(EXTRA_USER_ID, usuarioId)
                putExtra(EXTRA_USER_NOME, usuarioNome)
            }
            contex.startActivity(intent)
        }
    }
}
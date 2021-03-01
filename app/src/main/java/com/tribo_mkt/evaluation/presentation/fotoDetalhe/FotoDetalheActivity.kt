package com.tribo_mkt.evaluation.presentation.fotoDetalhe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import com.tribo_mkt.evaluation.presentation.fotos.FotosActivity
import kotlinx.android.synthetic.main.activity_fotos_detalhe.*
import kotlinx.android.synthetic.main.include_toolbar.*

class FotoDetalheActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos_detalhe)

        setupToolbar(toolBarMain, getString(R.string.foto_detale_title), true)

        PhotoBuild()
    }

    private fun PhotoBuild() {
        val url: String = intent.getStringExtra(EXTRA_PHOTO_URL)
        val titulo: String = intent.getStringExtra(EXTRA_PHOTO_TITLE)

        Picasso.get().load(url).into(imagem)
        imagemNome.text = titulo
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        FotosActivity.getStatIntent(
            this@FotoDetalheActivity,
            intent.getStringExtra(EXTRA_ALBUM_ID),
            intent.getStringExtra(EXTRA_USER_ID),
            intent.getStringExtra(EXTRA_USER_NOME)
        )
        return true
    }

    companion object {
        private const val EXTRA_ALBUM_ID = "EXTRA_ALBUM_ID"
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_USER_NOME = "EXTRA_USER_NOME"
        private const val EXTRA_PHOTO_URL = "EXTRA_PHOTO_URL"
        private const val EXTRA_PHOTO_TITLE = "EXTRA_PHOTO_TITLE"

        fun getStatIntent(
            contex: Context,
            albumId: String,
            usuarioId: String,
            usuarioNome: String,
            fotoUrl: String,
            FotoTitulo: String
        ) {
            val intent = Intent(contex, FotoDetalheActivity::class.java).apply {
                putExtra(EXTRA_ALBUM_ID, albumId)
                putExtra(EXTRA_USER_ID, usuarioId)
                putExtra(EXTRA_USER_NOME, usuarioNome)
                putExtra(EXTRA_PHOTO_URL, fotoUrl)
                putExtra(EXTRA_PHOTO_TITLE, FotoTitulo)
            }
            contex.startActivity(intent)
        }
    }
}
package com.tribo_mkt.evaluation.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tribo_mkt.evaluation.presentation.comentarios.ComentariosActivity
import com.tribo_mkt.evaluation.presentation.postagens.PostagensActivity

open class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, title: String, showBakcButton: Boolean = false) {
        toolbar.title = title
        setSupportActionBar(toolbar)

        if (showBakcButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}
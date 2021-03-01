package com.tribo_mkt.evaluation.presentation.inicio

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
import com.tribo_mkt.evaluation.data.model.Usuario
import com.tribo_mkt.evaluation.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.tribo_mkt.evaluation.utils.Status.*

class InicioActivity : BaseActivity() {

    private lateinit var viewModel: InicioViewModel
    private lateinit var adapter: InicioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        setupToolbar(toolBarMain, getString(R.string.inicio_title))

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            InicioViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.service))
        ).get(InicioViewModel::class.java)
    }

    private fun setupUI() {
        recycleUsuarios.layoutManager = LinearLayoutManager(this)
        adapter = InicioAdapter(arrayListOf())
        recycleUsuarios.addItemDecoration(
            DividerItemDecoration(
                recycleUsuarios.context,
                (recycleUsuarios.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleUsuarios.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        recycleUsuarios.visibility = View.VISIBLE
                        progressBarInicio.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users as MutableList<Usuario>) }
                    }
                    ERROR -> {
                        recycleUsuarios.visibility = View.VISIBLE
                        progressBarInicio.visibility = View.GONE
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBarInicio.visibility = View.VISIBLE
                        recycleUsuarios.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: MutableList<Usuario>) {
        users.sortBy { it.name }
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }
}
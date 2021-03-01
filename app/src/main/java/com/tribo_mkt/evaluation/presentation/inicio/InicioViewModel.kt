package com.tribo_mkt.evaluation.presentation.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.repository.EvaluationRepository
import com.tribo_mkt.evaluation.data.repository.IEvaluationRepository
import com.tribo_mkt.evaluation.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException

class InicioViewModel(val repository: IEvaluationRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
                return InicioViewModel(EvaluationRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}
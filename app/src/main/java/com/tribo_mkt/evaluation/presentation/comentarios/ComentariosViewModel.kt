package com.tribo_mkt.evaluation.presentation.comentarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.repository.EvaluationRepository
import com.tribo_mkt.evaluation.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException

class ComentariosViewModel (val evaluationRepository: EvaluationRepository) : ViewModel() {

    fun getComentarios(postId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = evaluationRepository.getComentarios(postId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComentariosViewModel::class.java)) {
                return ComentariosViewModel(EvaluationRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}
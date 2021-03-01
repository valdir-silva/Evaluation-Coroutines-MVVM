package com.tribo_mkt.evaluation.presentation.albuns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.repository.EvaluationRepository
import com.tribo_mkt.evaluation.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException

class AlbunsViewModel(val evaluationRepository: EvaluationRepository) : ViewModel() {

    fun getAlbuns(userId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = evaluationRepository.getAlbuns(userId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbunsViewModel::class.java)) {
                return AlbunsViewModel(EvaluationRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}
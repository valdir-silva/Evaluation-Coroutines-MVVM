package com.tribo_mkt.evaluation.presentation.inicio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.data.api.ApiHelper
import com.tribo_mkt.evaluation.data.model.*
import com.tribo_mkt.evaluation.data.repository.IEvaluationRepository
import com.tribo_mkt.evaluation.utils.Status
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InicioViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: InicioViewModel

    @Test
    fun `when view model getUsers get success`() {
        // Arrange
        val usuarios = listOf(
            Usuario("2", "Carlos", "carlossantos", "carlossantos@a.com", "123 12 456")
        )
        val mockRepository = MockRepository(Status.SUCCESS, usuarios)
        viewModel = InicioViewModel(mockRepository)

        // Act
        viewModel.getUsers()

        // Assert
        //TODO
    }

    @Test
    fun `when view model getUsers get api error`() {
        //TODO
    }


    class MockRepository(private val result: Status, _usuarios: List<Usuario>) :
        IEvaluationRepository {

        var usuarios: List<Usuario> = _usuarios
        override suspend fun getUsers(): List<Usuario> {
            return usuarios
        }

        override suspend fun getAlbuns(userId: String): List<Album> {
            TODO("Not yet implemented")
        }

        override suspend fun getFotos(albumId: String): List<Foto> {
            TODO("Not yet implemented")
        }

        override suspend fun getPostagens(userId: String): List<Postagem> {
            TODO("Not yet implemented")
        }

        override suspend fun getComentarios(postId: String): List<Comentario> {
            TODO("Not yet implemented")
        }
    }
}
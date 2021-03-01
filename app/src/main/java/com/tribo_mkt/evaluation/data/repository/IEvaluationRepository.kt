package com.tribo_mkt.evaluation.data.repository

import com.tribo_mkt.evaluation.data.model.*

interface IEvaluationRepository {

    suspend fun getUsers(): List<Usuario>
    suspend fun getAlbuns(userId: String): List<Album>
    suspend fun getFotos(albumId: String): List<Foto>
    suspend fun getPostagens(userId: String): List<Postagem>
    suspend fun getComentarios(postId: String): List<Comentario>
}
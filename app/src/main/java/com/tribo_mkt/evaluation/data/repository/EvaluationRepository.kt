package com.tribo_mkt.evaluation.data.repository

import com.tribo_mkt.evaluation.data.api.ApiHelper

class EvaluationRepository(private val apiHelper: ApiHelper) : IEvaluationRepository {

    override suspend fun getUsers() = apiHelper.getUsers()
    override suspend fun getAlbuns(userId: String) = apiHelper.getAlbuns(userId)
    override suspend fun getFotos(albumId: String) = apiHelper.getFotos(albumId)
    override suspend fun getPostagens(userId: String) = apiHelper.getPostagens(userId)
    override suspend fun getComentarios(postId: String) = apiHelper.getComentarios(postId)
}
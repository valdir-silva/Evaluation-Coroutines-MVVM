package com.tribo_mkt.evaluation.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()
    suspend fun getAlbuns(userId: String) = apiService.getAlbuns(userId)
    suspend fun getFotos(albumId: String) = apiService.getFotos(albumId)
    suspend fun getPostagens(userId: String) = apiService.getPostagens(userId)
    suspend fun getComentarios(postId: String) = apiService.getComentarios(postId)
}
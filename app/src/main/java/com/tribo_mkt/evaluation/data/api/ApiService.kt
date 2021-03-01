package com.tribo_mkt.evaluation.data.api

import com.tribo_mkt.evaluation.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users/")
    suspend fun getUsers(): List<Usuario>

    @GET("albums")
    suspend fun getAlbuns(
        @Query("userId") userId: String
    ): List<Album>

    @GET("photos")
    suspend fun getFotos(
        @Query("albumId") albumId: String
    ): List<Foto>

    @GET("posts")
    suspend fun getPostagens(
        @Query("userId") userId: String
    ): List<Postagem>

    @GET("comments")
    suspend fun getComentarios(
        @Query("postId") postId: String
    ): List<Comentario>
}
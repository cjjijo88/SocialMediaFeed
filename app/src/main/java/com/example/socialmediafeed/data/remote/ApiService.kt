package com.example.socialmediafeed.data.remote

import com.example.socialmediafeed.data.model.Comment
import com.example.socialmediafeed.data.model.ImageData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */
interface ApiService {

    @GET("photos")
    suspend fun getImages(@Query("page") page: Int, @Query("per_page") perPage: Int): List<ImageData>

    @POST("like")
    suspend fun likeImage(@Body imageId: String): Response<Void>

    @POST("unlike")
    suspend fun unlikeImage(@Body imageId: String): Response<Void>

    @GET("photos/{id}/comments")
    suspend fun getComments(@Path("id") imageId: String?): List<Comment>

    @POST("photos/{id}/comments")
    suspend fun addComment(@Path("id") imageId: String, @Body comment: CommentRequest): Response<Void>
}

data class CommentRequest(val comment: String)

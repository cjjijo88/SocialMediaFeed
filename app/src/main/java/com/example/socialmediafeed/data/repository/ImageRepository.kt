package com.example.socialmediafeed.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.example.socialmediafeed.data.ImagePagingSource
import com.example.socialmediafeed.data.model.Comment
import com.example.socialmediafeed.data.model.ImageData
import com.example.socialmediafeed.data.remote.ApiService
import com.example.socialmediafeed.data.remote.CommentRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */

class ImageRepository @Inject constructor(private val apiService: ApiService) {

    fun getImagesPagingSource(): Flow<PagingData<ImageData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(apiService) }
        ).flow
    }

    suspend fun likeImage(imageId: String) {
        try {
            apiService.likeImage(imageId)
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error liking image: ${e.message}")
        }
    }

    suspend fun unlikeImage(imageId: String) {
        try {
            apiService.unlikeImage(imageId)
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error unliking image: ${e.message}")
        }
    }

    suspend fun getComments(imageId: String?): List<Comment> {
        return try {
            apiService.getComments(imageId)
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error fetching comments: ${e.message}")
            emptyList()
        }
    }

    suspend fun postComment(imageId: String, comment: String) {
        try {
            apiService.addComment(imageId, CommentRequest(comment))
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error posting comment: ${e.message}")
        }
    }
}

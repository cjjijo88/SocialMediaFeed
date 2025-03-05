package com.example.socialmediafeed.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.socialmediafeed.data.model.Comment
import com.example.socialmediafeed.data.model.ImageData
import com.example.socialmediafeed.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */
@HiltViewModel
class ImageFeedViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel(){

    private val _images = MutableStateFlow<PagingData<ImageData>>(PagingData.empty())
    val images: StateFlow<PagingData<ImageData>> = _images.asStateFlow()

    private val _comments = MutableStateFlow<Map<String?, List<Comment>>>(emptyMap())
    val comments: StateFlow<Map<String?, List<Comment>>> = _comments.asStateFlow()

    init {
        Log.d("ImageFeedViewModel", "ViewModel Created!")
        getImages()
    }

    fun getImages() {
        viewModelScope.launch {
            repository.getImagesPagingSource()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _images.value = pagingData
                }
        }
    }

    fun likeImage(imageId: String) {
        viewModelScope.launch {
            repository.likeImage(imageId)
            updateImageState(imageId, true)
        }
    }

    fun unlikeImage(imageId: String) {
        viewModelScope.launch {
            repository.unlikeImage(imageId)
            updateImageState(imageId, false)
        }
    }

    private fun updateImageState(imageId: String, isLiked: Boolean) {
        viewModelScope.launch {
            _images.value = _images.value.map { image ->
                if (image.id == imageId) {
                    image.copy(
                        likes = if (isLiked) image.likes + 1 else image.likes - 1,
                        comments = image.comments ?: emptyList()
                    )
                } else {
                    image
                }
            }
        }
    }


    fun fetchComments(imageId: String?) {
        viewModelScope.launch {
            val fetchedComments = repository.getComments(imageId)
            _comments.value += (imageId to fetchedComments)
        }
    }

    fun addComment(imageId: String, comment: String) {
        viewModelScope.launch {
            repository.postComment(imageId, comment)
            fetchComments(imageId)
        }
    }

}


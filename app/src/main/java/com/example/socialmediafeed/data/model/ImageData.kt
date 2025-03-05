package com.example.socialmediafeed.data.model

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */
data class ImageData(
    val id: String,
    val urls: ImageUrls,
    val user: User,
    val likes: Int,
    val comments: List<Comment> = emptyList()
)

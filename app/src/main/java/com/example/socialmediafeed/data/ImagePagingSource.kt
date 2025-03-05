package com.example.socialmediafeed.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmediafeed.data.model.ImageData
import com.example.socialmediafeed.data.remote.ApiService

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */
class ImagePagingSource(private val apiService: ApiService) : PagingSource<Int, ImageData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> {
        val page = params.key ?: 1
        try {
            Log.d("ImagePagingSource", "images")
            val images = apiService.getImages(page, params.loadSize)
            Log.d("ImagePagingSource", "images"+images.toString())
            return LoadResult.Page(
                data = images,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
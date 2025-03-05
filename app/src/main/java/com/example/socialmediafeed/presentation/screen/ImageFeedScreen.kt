package com.example.socialmediafeed.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.socialmediafeed.presentation.viewmodel.ImageFeedViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageFeedScreen(navController: NavController, viewModel: ImageFeedViewModel) {
    val images = viewModel.images.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Social Media Feed",
                    textAlign = TextAlign.Center) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.getImages()
                isRefreshing = false
            }
        ) {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(images.itemCount) { index ->
                    val image = images[index]
                    image?.let {
                        ImageCard(
                            imageData = it,
                            navController = navController,
                            onLikeClick = { viewModel.likeImage(it.id) },
                            onUnlikeClick = { viewModel.unlikeImage(it.id) },
                            onCommentClick = { comment -> viewModel.addComment(it.id,
                                comment.toString()
                            ) }
                        )
                    }
                }
            }
        }
    }
}

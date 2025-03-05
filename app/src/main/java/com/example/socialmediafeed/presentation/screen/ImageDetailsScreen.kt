package com.example.socialmediafeed.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmediafeed.data.model.ImageData
import com.example.socialmediafeed.presentation.viewmodel.ImageFeedViewModel

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavController, imageDetails: ImageData?, viewModel: ImageFeedViewModel) {

    val comments by viewModel.comments.collectAsState()

    LaunchedEffect(imageDetails?.id) {
        viewModel.fetchComments(imageDetails!!.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Image ID: ${imageDetails?.id}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = imageDetails?.urls,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show comments
            Text(text = "Comments:", fontWeight = FontWeight.Bold)
            comments[imageDetails?.id]?.let { commentList ->
                LazyColumn {
                    items(commentList) { comment ->
                        Text(text = "${comment.user}: ${comment.text}", modifier = Modifier.padding(4.dp))
                    }
                }
            } ?: Text(text = "No comments yet.")
        }
    }
}
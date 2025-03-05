package com.example.socialmediafeed.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmediafeed.data.model.ImageData

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */

@Composable
fun ImageCard(
    imageData: ImageData,
    navController: NavController,
    onLikeClick: () -> Unit,
    onUnlikeClick: () -> Unit,
    onCommentClick: (Any?) -> Unit
) {
    var showCommentDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("image_detail/${imageData.id}") }, // Navigate on click
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Load Image
            AsyncImage(
                model = imageData.urls.regular,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Photographer Name
            Text(
                text = "Photo by ${imageData.user.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Likes and Comments Count
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "👍 ${imageData.likes} Likes", color = Color.Gray)
                Text(text = "💬 ${imageData.comments?.size?: 0} Comments", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Like, Dislike, and Comment Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Like",
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = onUnlikeClick) {
                    Icon(
                        imageVector = Icons.Default.ThumbDown,
                        contentDescription = "Unlike",
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = { showCommentDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Comment,
                        contentDescription = "Comment",
                        tint = Color.Blue,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // Show Comment Dialog
            if (showCommentDialog) {
                CommentDialog(
                    imageId = imageData.id,
                    onDismiss = { showCommentDialog = false },
                    onPostComment = onCommentClick
                )
            }
        }
    }
}

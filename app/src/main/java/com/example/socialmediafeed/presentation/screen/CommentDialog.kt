package com.example.socialmediafeed.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */

@Composable
fun CommentDialog(
    imageId: String,
    onDismiss: () -> Unit,
    onPostComment: (String) -> Unit
) {
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Comment") },
        text = {
            Column {
                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Type your comment") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (comment.isNotBlank()) {
                        onPostComment(comment)
                        onDismiss()
                    }
                }
            ) {
                Text("Post")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
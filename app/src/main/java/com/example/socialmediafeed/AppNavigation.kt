package com.example.socialmediafeed

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.socialmediafeed.presentation.viewmodel.ImageFeedViewModel
import com.example.socialmediafeed.presentation.screen.ImageDetailScreen
import com.example.socialmediafeed.presentation.screen.ImageFeedScreen

/**
 * @Author: Jijo
 * @Date: 04-03-2025
 */

@Composable
fun AppNavigation(viewModel: ImageFeedViewModel = hiltViewModel())  {
    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = "feed") {
        composable("feed") {
            ImageFeedScreen(navController, viewModel)
        }

        composable("image_detail/{imageId}") { backStackEntry ->
            val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
            val images = viewModel.images.collectAsLazyPagingItems()
            val imageDetails = images.itemSnapshotList.items.find { it.id == imageId }
            ImageDetailScreen(navController, imageDetails, viewModel)
        }
    }
}
package com.example.dayread.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dayread.ui.screens.category.CategoryDestination
import com.example.dayread.ui.screens.category.CategoryScreen
import com.example.dayread.ui.screens.home.HomeDestination
import com.example.dayread.ui.screens.home.HomeScreen

@Composable
fun DayReadNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    linkWebView: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToCategory = {
                    navController.navigate("${CategoryDestination.route}/$it")
                }
            )
        }
        composable(
            route = CategoryDestination.routeWithArgs,
            arguments = listOf(navArgument(CategoryDestination.categoryEncodedArg) {
                type = NavType.StringType
            })
        ) {
            CategoryScreen(
                onNavigateUp = { navController.navigateUp() },
                linkWebView = linkWebView
            )
        }
    }
}
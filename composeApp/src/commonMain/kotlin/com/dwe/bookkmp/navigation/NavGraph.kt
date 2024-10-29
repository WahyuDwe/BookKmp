package com.dwe.bookkmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dwe.bookkmp.presentation.screen.details.DetailsScreen
import com.dwe.bookkmp.presentation.screen.home.HomeScreen
import com.dwe.bookkmp.presentation.screen.manage.ManageScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home,
    ) {
        composable<Home> {
            HomeScreen(
                onBookSelect = { bookId ->
                    navController.navigate(Details(bookId))
                },
                onCreateClick = {
                    navController.navigate(Manage())
                }
            )
        }

        composable<Details> { backStackEntry ->
            DetailsScreen(
                onEditClick = {
                    navController.navigate(Manage(backStackEntry.toRoute<Details>().bookId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Manage> { backStackEntry ->
            ManageScreen(
                id = backStackEntry.toRoute<Manage>().bookId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
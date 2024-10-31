package com.dwe.bookkmp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dwe.bookkmp.presentation.screen.details.DetailsScreen
import com.dwe.bookkmp.presentation.screen.home.HomeScreen
import com.dwe.bookkmp.presentation.screen.manage.ManageScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    SharedTransitionLayout {
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
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable<Details> { backStackEntry ->
                DetailsScreen(
                    onEditClick = {
                        navController.navigate(Manage(backStackEntry.toRoute<Details>().bookId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
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
}
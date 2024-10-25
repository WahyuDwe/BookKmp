package com.dwe.bookkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dwe.bookkmp.navigation.Details
import com.dwe.bookkmp.navigation.Home
import com.dwe.bookkmp.navigation.Manage
import com.dwe.bookkmp.presentation.screen.home.HomeScreen
import com.dwe.bookkmp.presentation.screen.manage.ManageScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            val navController: NavHostController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Home,
            ) {
                composable<Home> {
                    HomeScreen(
                        onBookSelect = {},
                        onCreateClick = {
                            navController.navigate(Manage)
                        }
                    )
                }

                composable<Details> { }

                composable<Manage> {
                    ManageScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
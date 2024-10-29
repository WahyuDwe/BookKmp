package com.dwe.bookkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwe.bookkmp.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavGraph(navController)
        }
    }
}

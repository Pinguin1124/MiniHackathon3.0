package com.example.myapplication123.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication123.ui.screens.GameDetailScreen
import com.example.myapplication123.ui.screens.HomeScreen
import com.example.myapplication123.ui.screens.mini.MiniMemory
import com.example.myapplication123.ui.screens.mini.MiniPuzzle
import com.example.myapplication123.ui.screens.mini.MiniReaction

@Composable
fun GamesApp() {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    games = sampleGames,
                    onGameClick = { gameId ->
                        navController.navigate("detail/$gameId")
                    }
                )
            }

            composable("detail/{gameId}") { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()
                val game = sampleGames.find { it.id == gameId }

                GameDetailScreen(
                    game = game,
                    onBack = { navController.popBackStack() },
                    onPlayGame = { id ->
                        navController.navigate("mini/$id")
                    }
                )
            }

            composable("mini/{gameId}") { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()

                when (gameId) {
                    1 -> MiniMemory()
                    2 -> MiniReaction()
                    3 -> MiniPuzzle()
                    else -> MiniMemory()
                }
            }
        }
    }
}
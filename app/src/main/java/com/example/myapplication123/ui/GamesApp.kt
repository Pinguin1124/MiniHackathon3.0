package com.example.myapplication123.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun GamesApp() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") {
                    HomeScreen(
                        games = sampleGames,
                        onGameClick = { gameId ->
                            navController.navigate("game_detail/$gameId")
                        }
                    )
                }

                composable("game_detail/{gameId}") { backStackEntry ->
                    val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()
                    val game = sampleGames.firstOrNull { it.id == gameId }

                    GameDetailScreen(
                        game = game,
                        onBack = { navController.popBackStack() },
                        onPlayGame = { id ->
                            when (id) {
                                1 -> navController.navigate("mini_memory")
                                2 -> navController.navigate("mini_reaction")
                                3 -> navController.navigate("mini_puzzle")
                            }
                        }
                    )
                }

                composable("mini_memory") {
                    MiniMemory()
                }

                composable("mini_reaction") {
                    MiniReaction()
                }

                composable("mini_puzzle") {
                    MiniPuzzle()
                }
            }
        }
    }
}

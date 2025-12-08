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

            // ToDo-Liste State
            var todos by remember {
                mutableStateOf(
                    listOf<TodoItem>()
                )
            }
            var nextTodoId by remember { mutableStateOf(1) }
            var playedGamesCount by remember { mutableStateOf(0) }

            // Funktion zum Prüfen ob Spiele gesperrt sind (wenn eine Aufgabe überfällig ist)
            val gamesBlocked = todos.any { it.isOverdue() }

            // Berechne erlaubte Spiele basierend auf erledigten Aufgaben
            val allowedGames = todos
                .filter { it.completedAt != null }
                .sumOf { it.getAllowedGames() }

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") {
                    TodoHomeScreen(
                        todos = todos,
                        allowedGames = allowedGames,
                        onAddTodo = { title, desc ->
                            todos = todos + TodoItem(
                                id = nextTodoId++,
                                title = title,
                                description = desc
                            )
                        },
                        onCompleteTodo = { id ->
                            todos = todos.map {
                                if (it.id == id && it.completedAt == null) {
                                    it.copy(completedAt = System.currentTimeMillis())
                                } else {
                                    it
                                }
                            }
                        },
                        onDeleteTodo = { id ->
                            todos = todos.filter { it.id != id }
                        },
                        onNavigateToGames = {
                            navController.navigate("games")
                        }
                    )
                }

                composable("games") {
                    GamesSelectionScreen(
                        games = sampleGames,
                        allowedGames = allowedGames,
                        playedGamesCount = playedGamesCount,
                        onGameClick = { gameId ->
                            if (playedGamesCount < allowedGames) {
                                playedGamesCount++
                                navController.navigate("game_detail/$gameId")
                            }
                        },
                        onBackToTodos = {
                            navController.navigate("home")
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
                            if (gamesBlocked || playedGamesCount >= allowedGames) {
                                navController.navigate("home")
                            } else {
                                when (id) {
                                    1 -> navController.navigate("mini_memory")
                                    2 -> navController.navigate("mini_reaction")
                                    3 -> navController.navigate("mini_puzzle")
                                }
                            }
                        }
                    )
                }

                composable("mini_memory") {
                    if (gamesBlocked || playedGamesCount >= allowedGames) {
                        LaunchedEffect(Unit) {
                            navController.navigate("home")
                        }
                    } else {
                        MiniMemory()
                    }
                }

                composable("mini_reaction") {
                    if (gamesBlocked || playedGamesCount >= allowedGames) {
                        LaunchedEffect(Unit) {
                            navController.navigate("home")
                        }
                    } else {
                        MiniReaction()
                    }
                }

                composable("mini_puzzle") {
                    if (gamesBlocked || playedGamesCount >= allowedGames) {
                        LaunchedEffect(Unit) {
                            navController.navigate("home")
                        }
                    } else {
                        MiniPuzzle()
                    }
                }
            }
        }
    }
}
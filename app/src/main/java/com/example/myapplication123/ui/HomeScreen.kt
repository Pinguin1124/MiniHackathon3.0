package com.example.myapplication123.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    games: List<Game>,
    onGameClick: (Int) -> Unit,
    gamesBlocked: Boolean,
    onTodoClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spiele") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1a1a2e)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a2e))
                .padding(padding)
        ) {
            // Warnung wenn Spiele gesperrt sind
            if (gamesBlocked) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFff6b6b)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔒 Spiele gesperrt!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Erledige zuerst deine überfälligen Aufgaben!",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onTodoClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                "Zu den Aufgaben",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            // Spieleliste
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(games) { index, game ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !gamesBlocked) {
                                if (!gamesBlocked) onGameClick(game.id)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (gamesBlocked)
                                Color(0xFF16213e).copy(alpha = 0.3f)
                            else
                                Color(0xFF16213e)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Spiel-Emoji
                            Text(
                                text = when(game.id) {
                                    1 -> "🧠"
                                    2 -> "⚡"
                                    3 -> "🎨"
                                    else -> "🎮"
                                },
                                fontSize = 48.sp,
                                modifier = Modifier.padding(end = 16.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = game.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (gamesBlocked) Color.Gray else Color.White
                                )
                                Text(
                                    text = game.description,
                                    fontSize = 14.sp,
                                    color = if (gamesBlocked)
                                        Color.Gray
                                    else
                                        Color.White.copy(alpha = 0.7f)
                                )
                            }

                            if (gamesBlocked) {
                                Text(
                                    text = "🔒",
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesSelectionScreen(
    games: List<Game>,
    allowedGames: Int,
    playedGamesCount: Int,
    onGameClick: (Int) -> Unit,
    onBackToTodos: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spiele auswählen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1a1a2e)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackToTodos) {
                        Text("←", fontSize = 24.sp, color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a2e))
                .padding(padding)
        ) {
            // Anzeige verbleibender Spiele
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF16213e)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎮 ${allowedGames - playedGamesCount} / $allowedGames",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00d4ff)
                    )
                    Text(
                        text = "Spiele übrig",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    if (playedGamesCount >= allowedGames) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Alle Spiele aufgebraucht!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFff6b6b),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Erledige mehr Aufgaben für weitere Spiele!",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Spieleliste
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(games) { index, game ->
                    val canPlay = playedGamesCount < allowedGames

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = canPlay) {
                                if (canPlay) onGameClick(game.id)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (canPlay)
                                Color(0xFF16213e)
                            else
                                Color(0xFF16213e).copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Spiel-Emoji
                            Text(
                                text = when(game.id) {
                                    1 -> "🧠"
                                    2 -> "⚡"
                                    3 -> "🎨"
                                    else -> "🎮"
                                },
                                fontSize = 48.sp,
                                modifier = Modifier.padding(end = 16.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = game.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (canPlay) Color.White else Color.Gray
                                )
                                Text(
                                    text = game.description,
                                    fontSize = 14.sp,
                                    color = if (canPlay)
                                        Color.White.copy(alpha = 0.7f)
                                    else
                                        Color.Gray
                                )
                            }

                            if (!canPlay) {
                                Text(
                                    text = "🔒",
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
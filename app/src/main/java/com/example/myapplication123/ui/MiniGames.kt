package com.example.myapplication123.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MiniMemory() {
    var cards by remember { mutableStateOf(createMemoryCards()) }
    var flippedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var matchedPairs by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var moves by remember { mutableStateOf(0) }
    var isChecking by remember { mutableStateOf(false) }

    LaunchedEffect(flippedIndices) {
        if (flippedIndices.size == 2 && !isChecking) {
            isChecking = true
            moves++
            delay(1000)

            val first = cards[flippedIndices[0]]
            val second = cards[flippedIndices[1]]

            if (first.value == second.value) {
                matchedPairs = matchedPairs + setOf(first.value)
            }

            flippedIndices = emptyList()
            isChecking = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a2e))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Memory Spiel",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Züge: $moves | Paare: ${matchedPairs.size}/8",
            fontSize = 18.sp,
            color = Color(0xFF00d4ff),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (matchedPairs.size == 8) {
            Text(
                text = "🎉 Gewonnen in $moves Zügen! 🎉",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00ff00),
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    cards = createMemoryCards()
                    flippedIndices = emptyList()
                    matchedPairs = emptySet()
                    moves = 0
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00d4ff))
            ) {
                Text("Neues Spiel")
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0 until 4) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        for (col in 0 until 4) {
                            val index = row * 4 + col
                            val card = cards[index]
                            val isFlipped = flippedIndices.contains(index) || matchedPairs.contains(card.value)

                            MemoryCard(
                                emoji = card.emoji,
                                isFlipped = isFlipped,
                                onClick = {
                                    if (!isChecking && flippedIndices.size < 2 && !isFlipped) {
                                        flippedIndices = flippedIndices + index
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryCard(emoji: String, isFlipped: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(if (isFlipped) 1f else 0.95f)

    Box(
        modifier = Modifier
            .size(80.dp)
            .scale(scale)
            .background(
                if (isFlipped) Color(0xFF00d4ff) else Color(0xFF16213e),
                RoundedCornerShape(12.dp)
            )
            .clickable(enabled = !isFlipped) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isFlipped) {
            Text(text = emoji, fontSize = 40.sp)
        } else {
            Text(text = "?", fontSize = 32.sp, color = Color.White)
        }
    }
}

data class MemoryCardData(val value: Int, val emoji: String)

fun createMemoryCards(): List<MemoryCardData> {
    val emojis = listOf("🎮", "🎯", "🎲", "🎨", "🎭", "🎪", "🎸", "🎺")
    val pairs = emojis.mapIndexed { index, emoji ->
        listOf(
            MemoryCardData(index, emoji),
            MemoryCardData(index, emoji)
        )
    }.flatten().shuffled()
    return pairs
}

@Composable
fun MiniReaction() {
    var gameState by remember { mutableStateOf<ReactionState>(ReactionState.Ready) }
    var reactionTime by remember { mutableStateOf(0L) }
    var bestTime by remember { mutableStateOf<Long?>(null) }
    var startTime by remember { mutableStateOf(0L) }

    LaunchedEffect(gameState) {
        if (gameState is ReactionState.Waiting) {
            delay(Random.nextLong(2000, 5000))
            gameState = ReactionState.Go
            startTime = System.currentTimeMillis()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (gameState) {
                    is ReactionState.Ready -> Color(0xFF1a1a2e)
                    is ReactionState.Waiting -> Color(0xFFff6b6b)
                    is ReactionState.Go -> Color(0xFF4ecdc4)
                    is ReactionState.Result -> Color(0xFF1a1a2e)
                }
            )
            .clickable(enabled = gameState is ReactionState.Go || gameState is ReactionState.Ready) {
                when (gameState) {
                    is ReactionState.Ready -> gameState = ReactionState.Waiting
                    is ReactionState.Go -> {
                        reactionTime = System.currentTimeMillis() - startTime
                        if (bestTime == null || reactionTime < bestTime!!) {
                            bestTime = reactionTime
                        }
                        gameState = ReactionState.Result(reactionTime)
                    }
                    else -> {}
                }
            }
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = gameState) {
            is ReactionState.Ready -> {
                Text(
                    text = "⚡ Reaktionszeit-Test",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                if (bestTime != null) {
                    Text(
                        text = "Beste Zeit: ${bestTime}ms",
                        fontSize = 20.sp,
                        color = Color(0xFF00d4ff),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                Button(
                    onClick = { gameState = ReactionState.Waiting },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00d4ff)),
                    modifier = Modifier.size(200.dp, 80.dp)
                ) {
                    Text("START", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "\nTippe auf START und klicke dann so schnell wie möglich, wenn der Bildschirm GRÜN wird!",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
            is ReactionState.Waiting -> {
                Text(
                    text = "⏳ Warte...",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "\nKlicke NICHT bevor es grün wird!",
                    fontSize = 18.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            is ReactionState.Go -> {
                Text(
                    text = "🟢 JETZT!",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            is ReactionState.Result -> {
                Text(
                    text = "${state.time}ms",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00d4ff),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = when {
                        state.time < 200 -> "🚀 Unglaublich schnell!"
                        state.time < 300 -> "⚡ Sehr gut!"
                        state.time < 400 -> "👍 Gut!"
                        else -> "💪 Weiter üben!"
                    },
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(
                    onClick = { gameState = ReactionState.Ready },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00d4ff))
                ) {
                    Text("Nochmal", fontSize = 20.sp)
                }
            }
        }
    }
}

sealed class ReactionState {
    object Ready : ReactionState()
    object Waiting : ReactionState()
    object Go : ReactionState()
    data class Result(val time: Long) : ReactionState()
}

@Composable
fun MiniPuzzle() {
    var grid by remember { mutableStateOf(createColorGrid()) }
    var targetColor by remember { mutableStateOf(getRandomColor()) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(30) }
    var isGameActive by remember { mutableStateOf(false) }

    LaunchedEffect(isGameActive) {
        if (isGameActive && timeLeft > 0) {
            delay(1000)
            timeLeft--
        } else if (timeLeft == 0) {
            isGameActive = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a2e))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎨 Farb-Puzzle",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        if (!isGameActive && timeLeft == 30) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "Finde alle Felder mit der Zielfarbe!",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = {
                        isGameActive = true
                        score = 0
                        timeLeft = 30
                        grid = createColorGrid()
                        targetColor = getRandomColor()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00d4ff)),
                    modifier = Modifier.size(200.dp, 70.dp)
                ) {
                    Text("START", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Punkte: $score",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00d4ff)
                    )
                }
                Column {
                    Text(
                        text = "⏱️ $timeLeft",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (timeLeft <= 5) Color(0xFFff6b6b) else Color.White
                    )
                }
            }

            if (isGameActive) {
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Finde: ",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(targetColor, RoundedCornerShape(8.dp))
                    )
                }
            }

            if (timeLeft == 0) {
                Text(
                    text = "⏰ Zeit abgelaufen!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFff6b6b),
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Endpunktzahl: $score",
                    fontSize = 20.sp,
                    color = Color(0xFF00d4ff),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        isGameActive = true
                        score = 0
                        timeLeft = 30
                        grid = createColorGrid()
                        targetColor = getRandomColor()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00d4ff))
                ) {
                    Text("Nochmal spielen")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0 until 6) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        for (col in 0 until 5) {
                            val index = row * 5 + col
                            if (index < grid.size) {
                                val cell = grid[index]
                                ColorCell(
                                    color = cell.color,
                                    isFound = cell.isFound,
                                    enabled = isGameActive && !cell.isFound,
                                    onClick = {
                                        if (cell.color == targetColor) {
                                            grid = grid.toMutableList().apply {
                                                this[index] = cell.copy(isFound = true)
                                            }
                                            score += 10

                                            if (grid.none { it.color == targetColor && !it.isFound }) {
                                                targetColor = getRandomColor()
                                                grid = createColorGrid()
                                                score += 20
                                            }
                                        } else {
                                            score = maxOf(0, score - 5)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorCell(color: Color, isFound: Boolean, enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                if (isFound) Color.DarkGray else color,
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isFound) {
            Text(
                text = "✓",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00ff00)
            )
        }
    }
}

data class ColorCell(val color: Color, val isFound: Boolean = false)

fun createColorGrid(): List<ColorCell> {
    val colors = listOf(
        Color(0xFFff6b6b),
        Color(0xFF4ecdc4),
        Color(0xFFffe66d),
        Color(0xFFa8e6cf),
        Color(0xFFff8c94)
    )

    return List(30) {
        ColorCell(colors.random())
    }
}

fun getRandomColor(): Color {
    val colors = listOf(
        Color(0xFFff6b6b),
        Color(0xFF4ecdc4),
        Color(0xFFffe66d),
        Color(0xFFa8e6cf),
        Color(0xFFff8c94)
    )
    return colors.random()
}
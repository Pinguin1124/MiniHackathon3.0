package com.example.myapplication123.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    todos: List<TodoItem>,
    onAddTodo: (String, String) -> Unit,
    onToggleTodo: (Int) -> Unit,
    onDeleteTodo: (Int) -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // Timer zum Aktualisieren der Anzeige
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aufgaben erledigen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1a1a2e)
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 24.sp, color = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF00d4ff)
            ) {
                Text("+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a2e))
                .padding(padding)
        ) {
            // Aufgabenliste
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "📝",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Keine Aufgaben vorhanden",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Füge eine Aufgabe hinzu!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos) { todo ->
                        TodoCard(
                            todo = todo,
                            currentTime = currentTime,
                            onComplete = { onToggleTodo(todo.id) },
                            onDelete = { onDeleteTodo(todo.id) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AddTodoDialog(
                onDismiss = { showDialog = false },
                onAdd = { title, desc ->
                    onAddTodo(title, desc)
                    showDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoHomeScreen(
    todos: List<TodoItem>,
    allowedGames: Int,
    onAddTodo: (String, String) -> Unit,
    onCompleteTodo: (Int) -> Unit,
    onDeleteTodo: (Int) -> Unit,
    onNavigateToGames: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // Timer zum Aktualisieren der Anzeige
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aufgaben erledigen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1a1a2e)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF00d4ff)
            ) {
                Text("+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a2e))
                .padding(padding)
        ) {
            // Belohnungsanzeige
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        allowedGames >= 3 -> Color(0xFF00ff00)
                        allowedGames >= 2 -> Color(0xFF4ecdc4)
                        allowedGames >= 1 -> Color(0xFFffe66d)
                        else -> Color(0xFFff6b6b)
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when {
                            allowedGames >= 3 -> "🎮🎮🎮"
                            allowedGames >= 2 -> "🎮🎮"
                            allowedGames >= 1 -> "🎮"
                            else -> "🔒"
                        },
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when {
                            allowedGames >= 3 -> "Super schnell! 3 Spiele verfügbar!"
                            allowedGames >= 2 -> "Gut gemacht! 2 Spiele verfügbar!"
                            allowedGames >= 1 -> "Ok! 1 Spiel verfügbar!"
                            else -> "Keine Spiele verfügbar!"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    if (allowedGames > 0) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onNavigateToGames,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                "Zu den Spielen",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            // Belohnungssystem Info
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF16213e)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "⏱️ Belohnungssystem:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("⚡ < 10 Min = 3 Spiele", fontSize = 14.sp, color = Color(0xFF00ff00))
                    Text("💚 10-20 Min = 2 Spiele", fontSize = 14.sp, color = Color(0xFF4ecdc4))
                    Text("💛 20-30 Min = 1 Spiel", fontSize = 14.sp, color = Color(0xFFffe66d))
                    Text("❌ > 30 Min = Keine Spiele", fontSize = 14.sp, color = Color(0xFFff6b6b))
                }
            }

            // Aufgabenliste
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "📝",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Keine Aufgaben vorhanden",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Füge eine Aufgabe hinzu!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos) { todo ->
                        TodoCard(
                            todo = todo,
                            currentTime = currentTime,
                            onComplete = { onCompleteTodo(todo.id) },
                            onDelete = { onDeleteTodo(todo.id) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AddTodoDialog(
                onDismiss = { showDialog = false },
                onAdd = { title, desc ->
                    onAddTodo(title, desc)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun TodoCard(
    todo: TodoItem,
    currentTime: Long,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val minutesPassed = (currentTime - todo.createdAt) / (60 * 1000)
    val secondsPassed = ((currentTime - todo.createdAt) / 1000) % 60
    val isOverdue = todo.isOverdue()
    val isCompleted = todo.completedAt != null

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCompleted -> Color(0xFF00ff00).copy(alpha = 0.3f)
                isOverdue -> Color(0xFFff6b6b)
                minutesPassed < 10 -> Color(0xFF00ff00).copy(alpha = 0.2f)
                minutesPassed < 20 -> Color(0xFF4ecdc4).copy(alpha = 0.2f)
                minutesPassed < 30 -> Color(0xFFffe66d).copy(alpha = 0.2f)
                else -> Color(0xFFff6b6b).copy(alpha = 0.2f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = todo.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    if (todo.description.isNotEmpty()) {
                        Text(
                            text = todo.description,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isCompleted) {
                        val completionMinutes = todo.getMinutesPassed()
                        Text(
                            text = "✅ Erledigt in $completionMinutes Min | ${todo.getAllowedGames()} Spiele freigeschaltet",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00ff00)
                        )
                    } else if (isOverdue) {
                        Text(
                            text = "❌ ZEIT ABGELAUFEN! Keine Spiele verfügbar!",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "⏱️ ${minutesPassed} Min ${secondsPassed} Sek",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                minutesPassed < 10 -> Color(0xFF00ff00)
                                minutesPassed < 20 -> Color(0xFF4ecdc4)
                                minutesPassed < 30 -> Color(0xFFffe66d)
                                else -> Color(0xFFff6b6b)
                            }
                        )

                        val potentialGames = when {
                            minutesPassed < 10 -> 3
                            minutesPassed < 20 -> 2
                            minutesPassed < 30 -> 1
                            else -> 0
                        }

                        Text(
                            text = if (potentialGames > 0)
                                "→ Jetzt erledigen = $potentialGames Spiele"
                            else
                                "→ Zu spät!",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!isCompleted && !isOverdue) {
                        Button(
                            onClick = onComplete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00d4ff)
                            )
                        ) {
                            Text("✓ Erledigt")
                        }
                    }

                    IconButton(onClick = onDelete) {
                        Text("🗑️", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Neue Aufgabe (30 Min)",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "⏱️ Du hast 30 Minuten Zeit!",
                    fontSize = 14.sp,
                    color = Color(0xFFff6b6b),
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Aufgabe") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Details (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(title, description)
                    }
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00d4ff)
                )
            ) {
                Text("Starten")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}
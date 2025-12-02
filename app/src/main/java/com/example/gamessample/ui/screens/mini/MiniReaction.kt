package com.example.gamessample.ui.screens.mini


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MiniReaction() {
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("Drücke Start") }
    var waiting by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(16.dp)) {
        Text(message)
        Button(onClick = {
            message = "Warte..."
            waiting = true
            scope.launch {
                delay((1000L..3000L).random())
                message = "JETZT drücken!"
                waiting = false
            }
        }) {
            Text("Start")
        }
        if (!waiting && message == "JETZT drücken!") {
            Button(onClick = { message = "Reaktion registriert!" }) {
                Text("Tippe")
            }
        }
    }
}
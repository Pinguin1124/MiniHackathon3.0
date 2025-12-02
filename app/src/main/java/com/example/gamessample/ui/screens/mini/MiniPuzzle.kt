package com.example.gamessample.ui.screens.mini


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun MiniPuzzle() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Mini Puzzle (Demo)")
        Text(text = "Dies ist ein Platzhalter für ein Puzzle. Implementiere hier Logik für Schiebe- oder Match-Puzzle.")
    }
}
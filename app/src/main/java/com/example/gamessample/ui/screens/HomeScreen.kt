package com.example.gamessample.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.gamessample.ui.Game


@Composable
fun HomeScreen(games: List<Game>, onOpenGame: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(12.dp)) {
        items(games) { game ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onOpenGame(game.id) }) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = game.title)
                    Text(text = game.genre)
                }
            }
        }
    }
}
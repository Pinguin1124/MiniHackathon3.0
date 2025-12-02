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
fun GameDetailScreen(game: Game, onLaunchMini: (String) -> Unit) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = game.title)
        Text(text = game.genre)
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(game.miniGames) { mini ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onLaunchMini(mini.id) }) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = mini.name)
                        Text(text = mini.description)
                    }
                }
            }
        }
    }
}
```


## Mini-Spiele (sehr einfache, sofort lauffähige Beispiele)


### `MiniMemory.kt` — einfaches Tap-Matching (Demo)


```kotlin
package com.example.gamessample.ui.screens.mini


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun MiniMemory() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Mini Memory (Demo)")
        Text(text = "Tippe auf Karten — dies ist eine sehr einfache Demo-Anzeige.")
    }
}
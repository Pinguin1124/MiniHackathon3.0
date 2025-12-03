package com.example.gamessample.ui


data class MiniGame(
    val id: String,
    val name: String,
    val description: String
)


data class Game(
    val id: String,
    val title: String,
    val genre: String,
    val miniGames: List<MiniGame>
)


fun sampleGames(): List<Game> = listOf(
    Game(
        id = "fantasy",
        title = "Fantasy Quest",
        genre = "RPG",
        miniGames = listOf(
            MiniGame("dungeon", "Dungeon Escape", "Entkomme einem Labyrinth."),
            MiniGame("herbal", "Herbal Mixing", "Mische magische Tränke."),
            MiniGame("beast", "Beast Battle", "Kämpfe gegen Kreaturen.")
        )
    ),
    Game(
        id = "racer",
        title = "Galactic Racer",
        genre = "Rennspiel",
        miniGames = listOf(
            MiniGame("asteroid", "Asteroid Drift", "Weiche Asteroiden aus."),
            MiniGame("tuning", "Turbo Tuning", "Tuning-Minispiel."),
            MiniGame("star", "Star Challenge", "Rennen gegen Piloten.")
        )
    ),
    Game(
        id = "puzzle",
        title = "Puzzle Kingdom",
        genre = "Puzzle",
        miniGames = listOf(
            MiniGame("crystal", "Crystal Match", "Verbinde Kristalle."),
            MiniGame("rune", "Rune Logic", "Logikrätsel mit Runen."),
            MiniGame("castle", "Castle Builder", "Baue ein Schloss.")
        )
    )
)
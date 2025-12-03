package com.example.myapplication123.ui

data class Game(
    val id: Int,
    val name: String,
    val description: String
)

val sampleGames = listOf(
    Game(1, "Memory", "Ein Merkspiel"),
    Game(2, "Reaktion", "Teste deine Reaktion"),
    Game(3, "Puzzle", "Löse das Puzzle")
)
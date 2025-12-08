package com.example.myapplication123.ui

data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis(),
    val timeLimit: Long = 30 * 60 * 1000, // 30 Minuten in Millisekunden
    val completedAt: Long? = null
) {
    // Prüfen ob die Aufgabe überfällig ist
    fun isOverdue(): Boolean {
        return completedAt == null && (System.currentTimeMillis() - createdAt) > timeLimit
    }

    // Berechnen wie viele Minuten seit Erstellung vergangen sind
    fun getMinutesPassed(): Long {
        val endTime = completedAt ?: System.currentTimeMillis()
        return (endTime - createdAt) / (60 * 1000)
    }

    // Berechnen wie viele Spiele man spielen darf
    fun getAllowedGames(): Int {
        if (completedAt == null) return 0 // Nicht erledigt = keine Spiele

        val minutesPassed = getMinutesPassed()
        return when {
            minutesPassed < 10 -> 3  // Unter 10 Minuten = 3 Spiele
            minutesPassed < 20 -> 2  // 10-20 Minuten = 2 Spiele
            minutesPassed < 30 -> 1  // 20-30 Minuten = 1 Spiel
            else -> 0                 // Über 30 Minuten = keine Spiele
        }
    }
}
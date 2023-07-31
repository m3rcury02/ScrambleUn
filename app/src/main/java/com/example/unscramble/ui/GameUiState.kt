package com.example.unscramble.ui

data class GameUiState(
    val currentScrambledWord:String="",
    val isGuessedWordWrong:Boolean=false,
    val score: Int=0
)

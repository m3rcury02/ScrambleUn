package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel: ViewModel() {
    private val _uiState= MutableStateFlow(GameUiState())
    val uiState:StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord:String

    private var usedWords:MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    private fun pickRandShuffWord():String{
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandShuffWord()
        } else {
            usedWords.add(currentWord)
            ShuffWord(currentWord)
        }
    }
    private fun ShuffWord(word: String):String{
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while(String(tempWord) == word)
            tempWord.shuffle()
        return String(tempWord)
    }
    fun resetGame(){
        usedWords.clear()
        _uiState.value= GameUiState(currentScrambledWord = pickRandShuffWord())
    }

    fun updateUserGuess(guessedWord:String) {
        userGuess=guessedWord
    }
    fun checkGuess(){
        if (userGuess.equals(currentWord,ignoreCase = true)){
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
        }
        else{
            _uiState.update{currentState->
                currentState.copy(isGuessedWordWrong=true)
            }
        }
        updateUserGuess("")
    }

    init {
        resetGame()
    }
}